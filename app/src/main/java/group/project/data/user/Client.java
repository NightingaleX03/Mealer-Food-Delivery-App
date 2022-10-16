package group.project.data.user;

import group.project.data.Address;
import group.project.data.Credentials;
import group.project.data.PaymentInfo;
import group.project.firebase.FireBuffer;

public class Client extends User {

    private String firstName;
    private String lastName;
    private Address address;
    private PaymentInfo paymentInfo;

    public Client(Credentials credentials, String firstName, String lastName, Address address, PaymentInfo paymentInfo) {
        super(credentials);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.paymentInfo = paymentInfo;
    }

    public Client(FireBuffer buffer) {
        this.read(buffer);
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeEnum("type", Type.CLIENT);
        super.write(buffer);
        buffer.writeString("first_name", this.firstName);
        buffer.writeString("last_name", this.lastName);
        buffer.writeObject("address", this.address);
        buffer.writeObject("payment_info", this.paymentInfo);
    }

    @Override
    public void read(FireBuffer buffer) {
        super.read(buffer);
        this.firstName = buffer.readString("first_name");
        this.lastName = buffer.readString("last_name");
        this.address = buffer.readObject("address", Address::new);
        this.paymentInfo = buffer.readObject("payment_info", PaymentInfo::new);
    }

}
