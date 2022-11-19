package group.project.data;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public class Meal implements IFireSerializable {

    private String name;
    private String type;
    private String cuisine;
    private String ingredients;
    private String allergens;
    private String price;
    private String description;

    public Meal(String name, String type, String cuisine, String ingredients, String allergens,
                String price, String description) {
        this.name = name;
        this.type = type;
        this.cuisine = cuisine;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.price = price;
        this.description = description;
    }

    public Meal(FireBuffer buffer) {
        this.read(buffer);
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeString("name", this.name);
        buffer.writeString("type", this.type);
        buffer.writeString("cuisine", this.cuisine);
        buffer.writeString("ingredients", this.ingredients);
        buffer.writeString("allergens", this.allergens);
        buffer.writeString("price", this.price);
        buffer.writeString("description", this.description);
    }

    @Override
    public void read(FireBuffer buffer) {
        this.name = buffer.readString("name");
        this.type = buffer.readString("type");
        this.cuisine = buffer.readString("cuisine");
        this.ingredients = buffer.readString("ingredients");
        this.allergens = buffer.readString("allergens");
        this.price = buffer.readString("price");
        this.description = buffer.readString("description");
    }

}
