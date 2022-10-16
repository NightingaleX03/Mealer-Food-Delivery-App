package group.project.data.user;

import group.project.data.Credentials;
import group.project.firebase.FireBuffer;

public class Admin extends User {

    public Admin(Credentials credentials) {
        super(credentials);
    }

    public Admin(FireBuffer buffer) {
        this.read(buffer);
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeEnum("type", Type.ADMIN);
        super.write(buffer);
    }

    @Override
    public void read(FireBuffer buffer) {
        super.read(buffer);
    }

}
