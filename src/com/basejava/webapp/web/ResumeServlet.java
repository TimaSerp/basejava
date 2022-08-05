package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write("<html>\n" +
                "<head>\n" +
                "<title> Resumes </title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"2\" cellpadding=\"10\" cellspacing=\"0\">\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>UUID</th>\n" +
                "<th>Full name</th>\n" +
                "</tr>\n");
        for (Resume resume: storage.getAllSorted()) {
            writer.write("<tr>\n" +
                    "<td>" + resume.getUuid() + "</td>\n" +
                    "<td>" + resume.getFullName() + "</td>\n" +
                    "</tr>\n");
        }
        writer.write("</thead>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>");
    }
}
