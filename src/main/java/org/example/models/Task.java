package org.example.models;

import java.util.List;

public class Task {
    private String id;
    private String name;
    private String description;
    private String telephelyKod;
    private String telephelyNev;
    private String statusz;
    private String megjegyzes;
    private List<Equipment> equipmentList;

    // Constructors
    public Task() {}

    public Task(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Task(String id, String name, String description, String telephelyKod, String telephelyNev, String statusz, String megjegyzes, List<Equipment> equipmentList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.telephelyKod = telephelyKod;
        this.telephelyNev = telephelyNev;
        this.statusz = statusz;
        this.megjegyzes = megjegyzes;
        this.equipmentList = equipmentList;
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

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public static class Equipment {
        private String snDid;
        private String type;
        private String model;
        private String status;

        public Equipment(String snDid, String type, String model, String status) {
            this.snDid = snDid;
            this.type = type;
            this.model = model;
            this.status = status;
        }

        public String getSnDid() {
            return snDid;
        }

        public String getType() {
            return type;
        }

        public String getModel() {
            return model;
        }

        public String getStatus() {
            return status;
        }
    }
}