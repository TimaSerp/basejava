package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class BulletedListSection extends AbstractSection {
    private List<String> textList = new ArrayList<>();

    public BulletedListSection(ArrayList<String> textList) {
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
