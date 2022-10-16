package group.project.data.builder;

import group.project.data.Credentials;
import group.project.data.PaymentInfo;
import group.project.data.user.Client;

public class ClientBuilder extends UserBuilder<ClientBuilder, Client> {

    protected String firstName;
    protected String lastName;
    protected String address;
    protected PaymentInfo paymentInfo;

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

    public ClientBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ClientBuilder setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
        return this;
    }

    @Override
    public Client build() {
        return new Client(Credentials.create(this.principal, this.password), this.firstName,
                this.lastName, this.address, this.paymentInfo);
    }

}
