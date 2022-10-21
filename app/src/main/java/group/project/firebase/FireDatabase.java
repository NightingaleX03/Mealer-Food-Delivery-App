package group.project.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import group.project.data.builder.UserBuilder;
import group.project.data.user.User;
import group.project.util.Consumer;
import group.project.util.Function;

public class FireDatabase {

    private static FireDatabase INSTANCE;

    private final FirebaseFirestore fire;

    public FireDatabase(FirebaseFirestore fire) {
        this.fire = fire;
    }

    public static FireDatabase get() {
        if(INSTANCE == null) {
            INSTANCE = new FireDatabase(FirebaseFirestore.getInstance());
        }

        return INSTANCE;
    }

    public <T extends IFireSerializable> void write(String collection, String document, T value) {
        MemoryFireBuffer buffer = MemoryFireBuffer.empty();
        value.write(buffer);
        this.fire.collection(collection).document(document).set(buffer.toMap())
                    .addOnFailureListener(command -> {
                        System.err.println("Write Query Failed ==================");
                        command.fillInStackTrace();
                    });
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
                })
                 .addOnFailureListener(command -> {
                     System.err.println("Read Query Failed ==================");
                     command.fillInStackTrace();
                 });
    }

    public void login(String principal, String password, Consumer<User> onSuccess, Consumer<User> onFailure) {
        this.read("users", principal, User::fromBuffer, user -> {
            if(user == null) {
                onFailure.accept(null);
                return;
            }

            (user.getCredentials().canAuthenticate(principal, password) ? onSuccess : onFailure).accept(user);
        });
    }

    public void register(UserBuilder<?, ?> builder, Consumer<User> onSuccess, Consumer<User> onFailure) {
        User newUser = builder.build();

        this.read("users", newUser.getCredentials().getPrincipal(), User::fromBuffer, existingUser -> {
            if(existingUser == null) {
                this.write("users", newUser.getCredentials().getPrincipal(), newUser);
                onSuccess.accept(newUser);
            } else {
                onFailure.accept(existingUser);
            }
        });
    }

}
