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
    private boolean available;

    public Meal(String name, String type, String cuisine, String ingredients, String allergens,
                String price, String description, boolean available) {
        this.name = name;
        this.type = type;
        this.cuisine = cuisine;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.price = price;
        this.description = description;
        this.available = available;
    }

    public Meal(FireBuffer buffer) {
        this.read(buffer);
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getCuisine() {
        return this.cuisine;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public String getAllergens() {
        return this.allergens;
    }

    public String getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isAvailable() {
        return this.available;
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
        buffer.writeBoolean("available", this.available);
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
        this.available = buffer.readBoolean("available");
    }

    public String toEntryString() {
        return String.format("%s (%s %s)\n Ingredients: %s\n Allergens: %s\n Price: %s\n\n %s",
                this.name, this.type, this.cuisine,  this.ingredients, this.allergens, this.price, this.description);
    }

}
