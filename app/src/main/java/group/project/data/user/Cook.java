package group.project.data.user;

import java.util.ArrayList;
import java.util.List;

import group.project.data.Address;
import group.project.data.Complaint;
import group.project.data.Credentials;
import group.project.firebase.FireBuffer;

public class Cook extends User {

    private String firstName;
    private String lastName;
    private Address address;
    //TODO: void check image
    private String description;
    //TODO: menus
    private List<Complaint> complaints;

    public Cook(Credentials credentials, String firstName, String lastName, Address address,
                String description, List<Complaint> complaints) {
        super(credentials);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.description = description;
        this.complaints = complaints;
    }

    public Cook(FireBuffer buffer) {
        this.read(buffer);
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeEnum("type", Type.COOK);
        super.write(buffer);
        buffer.writeString("first_name", this.firstName);
        buffer.writeString("last_name", this.lastName);
        buffer.writeObject("address", this.address);
        buffer.writeString("description", this.description);
        buffer.writeList("complaints", this.complaints);
    }

    @Override
    public void read(FireBuffer buffer) {
        super.read(buffer);
        this.firstName = buffer.readString("first_name");
        this.lastName = buffer.readString("last_name");
        this.address = buffer.readObject("address", Address::new);
        this.description = buffer.readString("description");
        this.complaints = buffer.readList("complaints", ArrayList::new);
    }

}
