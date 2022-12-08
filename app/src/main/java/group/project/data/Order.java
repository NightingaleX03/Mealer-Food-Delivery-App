package group.project.data;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public class Order implements IFireSerializable {

    private String content;

    public Order(String content) {
        this.content = content;
    }

    public Order(FireBuffer buffer) {
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
