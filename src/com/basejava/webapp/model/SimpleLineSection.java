package com.basejava.webapp.model;

public class SimpleLineSection extends AbstractSection {
    private String sectionText;

    public SimpleLineSection(String sectionText) {
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
