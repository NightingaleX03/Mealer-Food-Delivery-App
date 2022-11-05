package group.project.data;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public class Address implements IFireSerializable {

    private String street;
    private int houseNumber;
    private String city;
    private String province;

    public Address(String street, int houseNumber, String city, String province) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.province = province;
    }

    public Address(FireBuffer buffer) {
        this.read(buffer);
    }

    public String getStreet() {
        return this.street;
    }

    public int getHouseNumber() {
        return this.houseNumber;
    }

    public String getCity() {
        return this.city;
    }

    public String getProvince() {
        return this.province;
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeString("street", this.street);
        buffer.writeInt("house_number", this.houseNumber);
        buffer.writeString("city", this.city);
        buffer.writeString("province", this.province);
    }

    @Override
    public void read(FireBuffer buffer) {
        this.street = buffer.readString("street");
        this.houseNumber = buffer.readInt("house_number");
        this.city = buffer.readString("city");
        this.province = buffer.readString("province");
    }

}
