package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListTextSection extends AbstractSection {
    private List<String> textList = new ArrayList<>();

    public ListTextSection(ArrayList<String> textList) {
        this.textList = textList;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String text: textList) {
            sb.append("- " + text + "\n");
        }
        return sb.toString();
    }
}
