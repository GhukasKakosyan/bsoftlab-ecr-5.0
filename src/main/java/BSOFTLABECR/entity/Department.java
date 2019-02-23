package BSOFTLABECR.entity;

public class Department {
    private int id;
    private String name;
    private int type;

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getType() {
        return this.type;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(int type) {
        this.type = type;
    }
}
