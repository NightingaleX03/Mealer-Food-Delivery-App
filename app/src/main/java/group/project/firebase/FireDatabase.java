package group.project.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import group.project.util.Consumer;
import group.project.util.Function;

public class FireDatabase {

    private final FirebaseFirestore fire;

    public FireDatabase(FirebaseFirestore fire) {
        this.fire = fire;
    }

    public <T extends IFireSerializable> void write(String collection, String document, T value) {
        MemoryFireBuffer buffer = MemoryFireBuffer.empty();
        value.write(buffer);
        this.fire.collection(collection).document(document).set(buffer.toMap());
    }

    public <T extends IFireSerializable> void read(String collection, String document,
                                                   Function<FireBuffer, T> value, Consumer<T> action) {
         this.fire.collection(collection).document(document).get()
                 .addOnSuccessListener(command -> {
                     Map<String, Object> data = command.getData();

                     if(data != null) {
                         MemoryFireBuffer buffer = MemoryFireBuffer.backing(data);
                         action.accept(value.apply(buffer));
                     } else {
                         action.accept(null);
                     }
                });
    }

}
