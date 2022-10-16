package group.project.data.builder;

import group.project.data.Credentials;
import group.project.data.user.Cook;

public class CookBuilder extends UserBuilder<CookBuilder, Cook> {

    protected String firstName;
    protected String lastName;
    protected String address;
    protected String description;

    protected CookBuilder() {

    }

    public CookBuilder setEmail(String email) {
        return this.setPrincipal(email);
    }

    public CookBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CookBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CookBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public CookBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Cook build() {
        return new Cook(Credentials.create(this.principal, this.password), this.firstName,
                this.lastName, this.address, this.description);
    }

}
