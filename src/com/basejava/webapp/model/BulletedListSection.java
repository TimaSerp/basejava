package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection {
    private List<String> items = new ArrayList<>();

    public BulletedListSection(ArrayList<String> items) {
        this.items = items;
    }

    public List<String> getitems() {
        return items;
    }

    public void setitems(List<String> items) {
        Objects.requireNonNull(items, "items must be not null");
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String text : items) {
            sb.append("- " + text + "\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulletedListSection that = (BulletedListSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
