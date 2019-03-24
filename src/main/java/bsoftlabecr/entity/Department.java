package bsoftlabecr.entity;

public class Department {
    private Integer id = null;
    private String name = null;
    private Integer type = null;

    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public Integer getType() {
        return this.type;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[" +
                this.id + ", " +
                this.name + ", " +
                this.type + "]";
    }
}
