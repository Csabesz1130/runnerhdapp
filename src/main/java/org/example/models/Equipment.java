package org.example.models;

public class Equipment {
    private String snDid;
    private String type;

    public Equipment() {
        // Default constructor
    }

    public Equipment(String snDid, String type) {
        this.snDid = snDid;
        this.type = type;
    }

    public String getSnDid() {
        return snDid;
    }

    public void setSnDid(String snDid) {
        this.snDid = snDid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "snDid='" + snDid + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    // You might want to override equals() and hashCode() methods if you plan to use this class in collections

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment equipment = (Equipment) o;

        if (!snDid.equals(equipment.snDid)) return false;
        return type.equals(equipment.type);
    }

    @Override
    public int hashCode() {
        int result = snDid.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}