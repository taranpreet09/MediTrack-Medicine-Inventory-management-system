package model;

public class Medicine {
    public String id;
    public String name;
    public String category;

    public Medicine(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String toString() {
        return "[" + id + "] " + name + " (" + category + ")";
    }
}
