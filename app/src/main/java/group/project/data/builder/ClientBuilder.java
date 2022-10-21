package group.project.data.builder;

import group.project.data.Address;
import group.project.data.Credentials;
import group.project.data.PaymentInfo;
import group.project.data.user.Client;

public class ClientBuilder extends UserBuilder<ClientBuilder, Client> {

    protected String firstName;
    protected String lastName;
    protected String street;
    protected int houseNumber;
    protected String city;
    protected String province;
    private String cardNumber;
    private int monthExpiration;
    private int yearExpiration;
    private int verificationValue;

    protected ClientBuilder() {

    }

    public ClientBuilder setEmail(String email) {
        return this.setPrincipal(email);
    }

    public ClientBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ClientBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ClientBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public ClientBuilder setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public ClientBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public ClientBuilder setProvince(String province) {
        this.province = province;
        return this;
    }

    public ClientBuilder setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public ClientBuilder setMonthExpiration(int monthExpiration) {
        this.monthExpiration = monthExpiration;
        return this;
    }

    public ClientBuilder setYearExpiration(int yearExpiration) {
        this.yearExpiration = yearExpiration;
        return this;
    }

    public ClientBuilder setVerificationValue(int verificationValue) {
        this.verificationValue = verificationValue;
        return this;
    }

    @Override
    public Client build() {
        return new Client(Credentials.create(this.principal, this.password), this.firstName,
                this.lastName, new Address(this.street, this.houseNumber, this.city, this.province),
                new PaymentInfo(this.cardNumber, this.monthExpiration, this.yearExpiration, this.verificationValue));
    }

}
