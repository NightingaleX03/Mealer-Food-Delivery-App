package group.project.data.user;

import group.project.data.Credentials;
import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;
import group.project.util.Optional;

public abstract class User implements IFireSerializable {

    private static User ACTIVE;

    private Credentials credentials;

    protected User() {

    }

    public User(Credentials credentials) {
        this.credentials = credentials;
    }

    public static User fromBuffer(FireBuffer buffer) {
        switch(buffer.readEnum("type", Type.class)) {
            case CLIENT: return new Client(buffer);
            case COOK: return new Cook(buffer);
            case ADMIN: return new Admin(buffer);
        }

        return null;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeObject("credentials", this.credentials);
    }

    @Override
    public void read(FireBuffer buffer) {
        this.credentials = buffer.readObject("credentials", Credentials::fromBuffer);
    }

    public static Optional<User> getActive() {
        return Optional.ofNullable(ACTIVE);
    }

    public static void setActive(User user) {
        ACTIVE = user;
    }

    public Type getType() {
        if(this instanceof Client) {
            return Type.CLIENT;
        } else if(this instanceof Cook) {
            return Type.COOK;
        } else if(this instanceof Admin) {
            return Type.ADMIN;
        }

        return null;
    }

    public enum Type {
        CLIENT, COOK, ADMIN
    }

}
