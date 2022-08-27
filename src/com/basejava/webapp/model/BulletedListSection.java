package com.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> items;

    BulletedListSection() {
    }

    public BulletedListSection(String... items) {
        this(Arrays.asList(items));
    }

    public BulletedListSection(List<String> items) {
        Objects.requireNonNull(items, "items must be not null");
        this.items = items;
    }

    @Override
    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String text : items) {
            sb.append(text);
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
