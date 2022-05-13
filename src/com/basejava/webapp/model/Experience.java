package com.basejava.webapp.model;

import java.util.ArrayList;

public class Experience extends AbstractSection {
    private ArrayList<Position> positions = new ArrayList<>();

    public Experience (ArrayList<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Position p: positions) {
            sb.append(p.getDateStart() + "-" + p.getDateFinish() + " " + p.getLink() + " " + p.getCompanyName() + " "
                    + p.getPost() + " " + p.getDefinition() + "\n");
        }
        return sb.toString();
    }
}
