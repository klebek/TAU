package pl.lebiedzinski;


public class Rastaman {

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrams() {
        return this.grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Rastaman(int id, String name, int grams, String type) {}

    public Rastaman() {}

    private int id;
    private String name;
    private int grams;
    private String type;

}