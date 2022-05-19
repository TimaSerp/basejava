package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private List<Position> positions = new ArrayList<>();

    public Organization(LocalDate dateStart, LocalDate dateFinish, String url,
                        String companyName, String post, String definition) {

        homePage = new Link(companyName, url);
        positions.add(new Position(dateStart, dateFinish, post, definition));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void addPost(Position post) {
        positions.add(post);
    }

    public static class Position {
        private final LocalDate dateStart;
        private final LocalDate dateFinish;
        private final String post;
        private final String definition;

        public Position(LocalDate dateStart, LocalDate dateFinish, String post, String definition) {
            Objects.requireNonNull(dateStart, "dateStart must be not null");
            Objects.requireNonNull(dateFinish, "dateFinish must be not null");
            Objects.requireNonNull(post, "post must be not null");
            this.dateStart = dateStart;
            this.dateFinish = dateFinish;
            this.post = post;
            this.definition = definition;
        }

        public LocalDate getDateStart() {
            return dateStart;
        }

        public LocalDate getDateFinish() {
            return dateFinish;
        }

        public String getPost() {
            return post;
        }

        public String getDefinition() {
            return definition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(dateStart, position.dateStart) && Objects.equals(dateFinish, position.dateFinish) && Objects.equals(post, position.post) && Objects.equals(definition, position.definition);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateStart, dateFinish, post, definition);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }
}
