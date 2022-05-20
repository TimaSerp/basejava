package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Experience extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> orgs;

    public Experience(Organization orgs) {
        this(Arrays.asList(orgs));
    }

    public Experience(List<Organization> orgs) {
        Objects.requireNonNull(orgs, "Organizations must be not null");
        for (int i = 0; i < orgs.toArray().length; i++) {
            Organization o = orgs.get(i);
            orgs.remove(o);
            for (int j = 0; j < orgs.toArray().length; j++) {
                Organization o2 = orgs.get(j);
                if (o.getHomePage().equals(o2.getHomePage())) {
                    o.addPost(o2.getPositions().get(0));
                    orgs.remove(o2);
                }
            }
            orgs.add(o);
        }
        this.orgs = orgs;
    }

    public List<Organization> getOrganizations() {
        return orgs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization o : orgs) {
            sb.append(o.getHomePage().getUrl() + " " + o.getHomePage().getName() + "\n");
            for (Organization.Position post : o.getPositions()) {
                sb.append(post.getDateStart() + "-" + post.getDateFinish() + " " + post.getPost() + " " +
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
