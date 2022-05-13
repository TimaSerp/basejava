package com.basejava.webapp.model;

import java.time.LocalDate;

public class Position {
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private String link;
    private String companyName;
    private String post;
    private String definition;

    public Position(LocalDate dateStart, LocalDate dateFinish, String link,
                    String companyName, String post, String definition) {
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.link = link;
        this.companyName = companyName;
        this.post = post;
        this.definition = definition;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
