package org.example.models;

import com.google.cloud.firestore.annotation.PropertyName;
import java.util.List;
import com.google.cloud.Timestamp;
import java.util.HashMap;
import java.util.Map;


public class Task {
    private String id;
    private String programName;
    private String lastModified;
    private String companyName;
    private List<Equipment> equipmentList;
    private Map<String, Object> dynamicFields;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("ProgramName")
    public String getProgramName() {
        return programName;
    }

    @PropertyName("ProgramName")
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @PropertyName("LastModified")
    public String getLastModified() {
        return lastModified;
    }

    @PropertyName("LastModified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @PropertyName("CompanyName")
    public String getCompanyName() {
        return companyName;
    }

    @PropertyName("CompanyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public Map<String, Object> getDynamicFields() {
        return dynamicFields;
    }

    public void setDynamicFields(Map<String, Object> dynamicFields) {
        this.dynamicFields = dynamicFields;
    }

    public void addDynamicField(String key, Object value) {
        this.dynamicFields.put(key, value);
    }

    public Object getDynamicField(String key) {
        return this.dynamicFields.get(key);
    }

    // Inner class for Equipment
    public static class Equipment {
        private Map<String, Object> dynamicFields;
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

        public Map<String, Object> getDynamicFields() {
            return dynamicFields;
        }

        public void setDynamicFields(Map<String, Object> dynamicFields) {
            this.dynamicFields = dynamicFields;
        }

        public void addDynamicField(String key, Object value) {
            this.dynamicFields.put(key, value);
        }

        public Object getDynamicField(String key) {
            return this.dynamicFields.get(key);
        }
    }
}