package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Experience extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> orgs;

    Experience() {
    }

    public Experience(Organization... orgs) {
        this(new ArrayList<>(Arrays.asList(orgs)));
    }

    public Experience(List<Organization> orgs) {
        Objects.requireNonNull(orgs, "Organizations must be not null");
        for (int i = 0; i < orgs.size(); i++) {
            Organization o = orgs.get(i);
            orgs.remove(o);
            for (int j = 0; j < orgs.toArray().length; j++) {
                Organization o2 = orgs.get(j);
                if (o.getHomePage().equals(o2.getHomePage())) {
                    o.addPost(o2.getPositions().get(0));
                    orgs.remove(o2);
                }
            }
            orgs.add(i, o);
        }
        this.orgs = orgs;
    }

    public Experience(Link link, Organization.Position position) {
        super();
    }

    @Override
    public List<Organization> getItems() {
        return orgs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization o : orgs) {
            sb.append(o.getHomePage().getUrl() + ", " + o.getHomePage().getName() + ", ");
            for (Organization.Position post : o.getPositions()) {
                sb.append(post.getDateStart() + "-" + post.getDateFinish() + ", " + post.getPost() + ", " +
                        post.getDefinition() + "\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(orgs, that.orgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgs);
    }
}
