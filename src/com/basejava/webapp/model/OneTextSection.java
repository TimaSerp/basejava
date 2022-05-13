package com.basejava.webapp.model;

public class OneTextSection extends AbstractSection {
    private String sectionText;

    public OneTextSection(String sectionText) {
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
