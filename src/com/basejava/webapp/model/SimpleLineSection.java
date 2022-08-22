package com.basejava.webapp.model;

import java.util.Objects;

public class SimpleLineSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String sectionText;

    SimpleLineSection() {
    }

    public SimpleLineSection(String sectionText) {
        Objects.requireNonNull(sectionText, "sectionText must be not null");
        this.sectionText = sectionText;
    }

    @Override
    public String getItems() {
        return sectionText;
    }

    @Override
    public String toString() {
        return (sectionText);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleLineSection that = (SimpleLineSection) o;
        return Objects.equals(sectionText, that.sectionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionText);
    }
}
