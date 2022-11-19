package group.project.data.user;

import java.util.ArrayList;
import java.util.List;

import group.project.data.Address;
import group.project.data.Complaint;
import group.project.data.Credentials;
import group.project.data.Meal;
import group.project.firebase.FireBuffer;

public class Cook extends User {

    private String firstName;
    private String lastName;
    private Address address;
    //TODO: void check image
    private String description;
    private List<Meal> meals;
    private List<Complaint> complaints;
    private int suspensionDays;

    public Cook(Credentials credentials, String firstName, String lastName, Address address,
                String description, List<Meal> meals, List<Complaint> complaints) {
        super(credentials);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.description = description;
        this.meals = meals;
        this.complaints = complaints;
    }

    public Cook(FireBuffer buffer) {
        this.read(buffer);
    }

    public Address getAddress() {
        return this.address;
    }

    public List<Meal> getMeals() {
        return this.meals;
    }

    public List<Complaint> getComplaints() {
        return this.complaints;
    }

    public int getSuspensionDays() {
        return this.suspensionDays;
    }

    public void setSuspensionDays(int suspensionDays) {
        this.suspensionDays = suspensionDays;
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeEnum("type", Type.COOK);
        super.write(buffer);
        buffer.writeString("first_name", this.firstName);
        buffer.writeString("last_name", this.lastName);
        buffer.writeObject("address", this.address);
        buffer.writeString("description", this.description);
        buffer.writeObjectList("meals", this.meals);
        buffer.writeObjectList("complaints", this.complaints);
        buffer.writeInt("suspensionDays", this.suspensionDays);
    }

    @Override
    public void read(FireBuffer buffer) {
        super.read(buffer);
        this.firstName = buffer.readString("first_name");
        this.lastName = buffer.readString("last_name");
        this.address = buffer.readObject("address", Address::new);
        this.description = buffer.readString("description");
        this.meals = buffer.readObjectList("meals", ArrayList::new, Meal::new);
        this.complaints = buffer.readObjectList("complaints", ArrayList::new, Complaint::new);
        this.suspensionDays = buffer.readInt("suspensionDays");
    }

}
