package group.project.data.builder;

import group.project.data.Credentials;
import group.project.data.user.Admin;

public class AdminBuilder extends UserBuilder<AdminBuilder, Admin> {

    protected AdminBuilder() {

    }

    @Override
    public Admin build() {
        return new Admin(Credentials.create(this.principal, this.password));
    }

}
