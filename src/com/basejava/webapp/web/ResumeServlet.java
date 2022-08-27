package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isNew = (uuid == null || uuid.trim().length() == 0);
        Resume r;
        if (isNew) {
            r = new Resume(UUID.randomUUID().toString(), fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().length() == 0) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.putSection(type, new SimpleLineSection(value));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        String[] items = value.split("\n");
                        r.putSection(type, new BulletedListSection(items));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> orgs = new ArrayList<>();
                        String[] names = request.getParameterValues(type.name());
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < names.length; i++) {
                            String name = names[i];
                            List<Organization.Position> positions = new ArrayList<>();
                            if (name != null && name.trim().length() != 0) {
                                String[] startDates = request.getParameterValues(type.name() + "dateStart" + i);
                                String[] endDates = request.getParameterValues(type.name() + "dateFinish" + i);
                                String[] posts = request.getParameterValues(type.name() + "post" + i);
                                String[] definitions = request.getParameterValues(type.name() + "definition" + i);
                                for (int j = 0; j < posts.length; j++) {
                                    positions.add(new Organization.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), posts[j], definitions[j]));
                                }
                                orgs.add(new Organization(new Link(name, urls[i]), positions));
                            }
                        }
                        r.putSection(type, new Experience(orgs));
                        break;
                }
            }
        }
        if (isNew) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "add":
                r = new Resume("");
                r.putSection(SectionType.PERSONAL, new SimpleLineSection(""));
                r.putSection(SectionType.OBJECTIVE, new SimpleLineSection(""));
                r.putSection(SectionType.EXPERIENCE, new BulletedListSection(""));
                r.putSection(SectionType.ACHIEVEMENT, new BulletedListSection(""));
                r.putSection(SectionType.EXPERIENCE, new Experience(new Organization("", "", new Organization.Position())));
                r.putSection(SectionType.EDUCATION, new Experience(new Organization("", "", new Organization.Position())));
                break;
            case "view":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    if (section != null) {
                        r.putSection(type, section);
                    }
                }
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    if (section == null) {
                        switch (type) {
                            case OBJECTIVE:
                            case PERSONAL:
                                section = new SimpleLineSection("");
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                section = new BulletedListSection("");
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                section = new Experience(new Organization("", "", new Organization.Position()));
                                break;
                        }
                    }
                    r.putSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
