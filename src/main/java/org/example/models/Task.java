package org.example.models;

public class Task {
    private String id;
    private String name;
    private String description;
    private String telephelyKod;

    // Constructors, getters, and setters
    public Task() {}

    public Task(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

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

    public String getTelephely() {
        return telephelyKod; // Return the location code
    }

    public void setTelephelyKod(String telephelyKod) {
        this.telephelyKod = telephelyKod;
    }

    public void setStatusz(String ujStatusz) {
    }
}
