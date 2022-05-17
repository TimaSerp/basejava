package com.basejava.webapp.model;

import java.util.Objects;

public class SimpleLineSection extends AbstractSection {
    private String sectionText;

    public SimpleLineSection(String sectionText) {
        Objects.requireNonNull(sectionText, "sectionText must be not null");
        this.sectionText = sectionText;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    @Override
    public String toString() {
        return (sectionText + "\n");
    }
}
