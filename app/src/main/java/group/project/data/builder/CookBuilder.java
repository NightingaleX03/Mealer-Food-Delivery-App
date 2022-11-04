package group.project.data.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import group.project.data.Address;
import group.project.data.Complaint;
import group.project.data.Credentials;
import group.project.data.user.Cook;

public class CookBuilder extends UserBuilder<CookBuilder, Cook> {

    protected String firstName;
    protected String lastName;
    protected String street;
    protected int houseNumber;
    protected String city;
    protected String province;
    protected String description;
    protected List<Complaint> complaints = new ArrayList<>();

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

    public CookBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public CookBuilder setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public CookBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public CookBuilder setProvince(String province) {
        this.province = province;
        return this;
    }

    public CookBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CookBuilder addComplaint(Complaint... complaints) {
        this.complaints.addAll(Arrays.asList(complaints));
        return this;
    }

    @Override
    public Cook build() {
        return new Cook(Credentials.create(this.principal, this.password), this.firstName,
                this.lastName, new Address(this.street, this.houseNumber, this.city, this.province),
                this.description, this.complaints);
    }

}
