package group.project.data;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public class PaymentInfo implements IFireSerializable {

    private String cardNumber;
    private int monthExpiration;
    private int yearExpiration;
    private int verificationValue;

    public PaymentInfo(String cardNumber, int monthExpiration, int yearExpiration, int verificationValue) {
        this.cardNumber = cardNumber;
        this.monthExpiration = monthExpiration;
        this.yearExpiration = yearExpiration;
        this.verificationValue = verificationValue;
    }

    public PaymentInfo(FireBuffer buffer) {
        this.read(buffer);
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public int getMonthExpiration() {
        return this.monthExpiration;
    }

    public int getYearExpiration() {
        return this.yearExpiration;
    }

    public int getVerificationValue() {
        return this.verificationValue;
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeString("card_number", this.cardNumber);
        buffer.writeInt("month_expiration", this.monthExpiration);
        buffer.writeInt("year_expiration", this.yearExpiration );
        buffer.writeInt("verification_value", this.verificationValue);
    }

    @Override
    public void read(FireBuffer buffer) {
        this.cardNumber = buffer.readString("card_number");
        this.monthExpiration = buffer.readInt("month_expiration");
        this.yearExpiration = buffer.readInt("year_expiration");
        this.verificationValue = buffer.readInt("verification_value");
    }

}
