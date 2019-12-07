package bsoftlabecr.entity;

import java.util.Arrays;

public class Cashier {
    private Integer id = null;
    private String name = null;
    private Integer[] deps = null;

    public Integer getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public Integer[] getDeps() {
        return this.deps;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDeps(Integer[] deps) {
        this.deps = deps;
    }

    @Override
    public String toString() {
        return "[" +
                "id: " + this.id + ", " +
                "name: " + this.name + ", " +
                "deps: " + Arrays.toString(this.deps) + "]";
    }
}
