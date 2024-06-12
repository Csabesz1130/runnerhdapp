package org.example.models;

public class Task {
    private String id;
    private String name;
    private String description;
    private String telephelyKod;
    private String telephelyNev;
    private String statusz;
    private String megjegyzes;

    // Constructors
    public Task() {}

    public Task(String id, String name, String description, String telephelyKod, String telephelyNev, String statusz, String megjegyzes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.telephelyKod = telephelyKod;
        this.telephelyNev = telephelyNev;
        this.statusz = statusz;
        this.megjegyzes = megjegyzes;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephelyKod() {
        return telephelyKod;
    }

    public void setTelephelyKod(String telephelyKod) {
        this.telephelyKod = telephelyKod;
    }

    public String getTelephelyNev() {
        return telephelyNev;
    }

    public void setTelephelyNev(String telephelyNev) {
        this.telephelyNev = telephelyNev;
    }

    public String getStatusz() {
        return statusz;
    }

    public void setStatusz(String statusz) {
        this.statusz = statusz;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }
}