package group.project.data;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public class PaymentInfo implements IFireSerializable {

    public PaymentInfo(FireBuffer buffer) {
        this.read(buffer);
    }

    @Override
    public void write(FireBuffer buffer) {

    }

    @Override
    public void read(FireBuffer buffer) {

    }

}
