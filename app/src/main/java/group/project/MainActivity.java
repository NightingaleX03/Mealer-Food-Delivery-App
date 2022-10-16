package group.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import group.project.data.builder.ClientBuilder;
import group.project.data.builder.UserBuilder;
import group.project.data.user.User;
import group.project.firebase.FireDatabase;
import group.project.util.Consumer;

public class MainActivity extends AppCompatActivity {

    private FireDatabase database;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);
        this.database = new FireDatabase(FirebaseFirestore.getInstance());

        ClientBuilder client = UserBuilder.ofClient()
                .setEmail("username@email.com")
                .setPassword("my_password")
                .setFirstName("John")
                .setLastName("Doe")
                .setAddress("1234 Some Place");

        this.register(client,
                user -> {
                    System.out.println("User registered successfully! ============================");
                },
                user -> {
                    System.out.println("User already exists. ============================");
                });


        this.login("username@email.com", "my_password",
                user -> {
                    System.out.println("User logged in successfully! ============================");
                },
                user -> {
                    System.out.println("User failed to log in. Invalid credentials. ============================");
                });
    }

    public void login(String principal, String password, Consumer<User> onSuccess, Consumer<User> onFailure) {
        this.database.read("users", principal, User::fromBuffer, user -> {
            if(user == null) {
                onFailure.accept(null);
                return;
            }

            (user.getCredentials().canAuthenticate(principal, password) ? onSuccess : onFailure).accept(user);
        });
    }

    public void register(UserBuilder<?, ?> builder, Consumer<User> onSuccess, Consumer<User> onFailure) {
        User newUser = builder.build();

        this.database.read("users", newUser.getCredentials().getPrincipal(), User::fromBuffer, existingUser -> {
            if(existingUser == null) {
                this.database.write("users", newUser.getCredentials().getPrincipal(), newUser);
                onSuccess.accept(newUser);
            } else {
                onFailure.accept(existingUser);
            }
       });
    }

}
