package group.project.data;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public class Complaint implements IFireSerializable {

    private String content;

    public Complaint(String content) {
        this.content = content;
    }

    public Complaint(FireBuffer buffer) {
        this.read(buffer);
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeString("content", this.content);
    }

    @Override
    public void read(FireBuffer buffer) {
        this.content = buffer.readString("content");
    }

}
