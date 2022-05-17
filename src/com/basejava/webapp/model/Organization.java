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

    public class Position {
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

            if (dateStart != null ? !dateStart.equals(position.dateStart) : position.dateStart != null) return false;
            if (dateFinish != null ? !dateFinish.equals(position.dateFinish) : position.dateFinish != null)
                return false;
            if (post != null ? !post.equals(position.post) : position.post != null) return false;
            if (definition != null ? !definition.equals(position.definition) : position.definition != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = dateStart != null ? dateStart.hashCode() : 0;
            result = 31 * result + (dateFinish != null ? dateFinish.hashCode() : 0);
            result = 31 * result + (post != null ? post.hashCode() : 0);
            result = 31 * result + (definition != null ? definition.hashCode() : 0);
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        if (positions != null ? !positions.equals(that.positions) : that.positions != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
        return result;
    }
}
