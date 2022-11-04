package group.project.data;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public class Complaint implements IFireSerializable {

    private String complainant;
    private String content;

    public Complaint(String complainant, String content) {
        this.complainant = complainant;
        this.content = content;
    }

    public Complaint(FireBuffer buffer) {
        this.read(buffer);
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeString("complainant", this.complainant);
        buffer.writeString("content", this.content);
    }

    @Override
    public void read(FireBuffer buffer) {
        this.complainant = buffer.readString("complainant");
        this.content = buffer.readString("content");
    }

}
