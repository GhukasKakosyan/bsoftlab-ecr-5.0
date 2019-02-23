package BSOFTLABECR.entity;

public class Cashier {
    private int id;
    private String name;
    private int[] deps;

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int[] getDeps() {
        return this.deps;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDeps(int[] deps) {
        this.deps = deps;
    }
}
