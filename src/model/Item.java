package model;

public abstract class Item {
    protected int id;
    protected String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}