package group.project.firebase;

public interface IFireSerializable {

    void write(FireBuffer buffer);

    void read(FireBuffer buffer);

}
