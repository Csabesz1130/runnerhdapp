package org.example.models;

import java.util.List;

public class Task {
    private String id;
    private String companyName;
    private String lastModified;
    private String programName;
    private boolean megNyitva;
    private boolean statuszNelkul;
    private List<Equipment> equipmentList;

    // Constructors
    public Task() {
    }

    public Task(String id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public Task(String id, String companyName, String lastModified, String programName,
                boolean megNyitva, boolean statuszNelkul, List<Equipment> equipmentList) {
        this.id = id;
        this.companyName = companyName;
        this.lastModified = lastModified;
        this.programName = programName;
        this.megNyitva = megNyitva;
        this.statuszNelkul = statuszNelkul;
        this.equipmentList = equipmentList;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public boolean isMegNyitva() {
        return megNyitva;
    }

    public void setMegNyitva(boolean megNyitva) {
        this.megNyitva = megNyitva;
    }

    public boolean isStatuszNelkul() {
        return statuszNelkul;
    }

    public void setStatuszNelkul(boolean statuszNelkul) {
        this.statuszNelkul = statuszNelkul;
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