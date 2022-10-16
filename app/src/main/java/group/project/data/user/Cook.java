package group.project.data.user;

import group.project.data.Credentials;
import group.project.firebase.FireBuffer;

public class Cook extends User {

    private String firstName;
    private String lastName;
    private String address;
    //TODO: void check image
    private String description;
    //TODO: menus

    public Cook(Credentials credentials, String firstName, String lastName, String address, String description) {
        super(credentials);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.description = description;
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
        buffer.writeString("address", this.address);
        buffer.writeString("description", this.description);
    }

    @Override
    public void read(FireBuffer buffer) {
        super.read(buffer);
        this.firstName = buffer.readString("first_name");
        this.lastName = buffer.readString("last_name");
        this.address = buffer.readString("address");
        this.description = buffer.readString("description");
    }

}
