package group.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import group.project.data.Address;
import group.project.data.PaymentInfo;
import group.project.data.builder.ClientBuilder;
import group.project.data.builder.UserBuilder;
import group.project.data.user.User;
import group.project.firebase.FireDatabase;
import group.project.util.BiConsumer;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);

        this.findViewById(R.id.loginbtn).setOnClickListener(view -> {
            this.tryLogin((username, password) -> {
                FireDatabase.get().login(username, password,
                        user -> {
                            this.startActivity(new Intent(this, WelcomeActivity.class));
                            User.setActive(user);
                        },
                        user -> {
                            ((EditText)this.findViewById(R.id.username)).setError("Invalid username.");
                            ((EditText)this.findViewById(R.id.password)).setError("Invalid password.");
                            Toast.makeText(this, "Invalid credentials, please try again", Toast.LENGTH_SHORT).show();
                        });
            });
        });

        this.findViewById(R.id.signUpAsClient).setOnClickListener(view -> {
            this.startActivity(new Intent(this, ClientRegisterActivity.class));
        });

        this.findViewById(R.id.signUpAsCook).setOnClickListener(view -> {
            this.startActivity(new Intent(this, CookRegisterActivity.class));
        });

        ClientBuilder client = UserBuilder.ofClient()
                .setEmail("username@email.com")
                .setPassword("my_password")
                .setFirstName("John")
                .setLastName("Doe")
                .setAddress(new Address("Some Place Ave.", 1234, "ottawa", "ON"))
                .setPaymentInfo(new PaymentInfo("1234567890", 10, 22, 314));

        FireDatabase.get().register(client,
                user -> {
                    System.out.println("User registered successfully! ============================");
                },
                user -> {
                    System.out.println("User already exists. ============================");
                });
    }

    public void tryLogin(BiConsumer<String, String> onSuccess) {
        EditText usernameButton = this.findViewById(R.id.username);
        EditText passwordButton = this.findViewById(R.id.password);
        String username = usernameButton.getText() == null ? "" : usernameButton.getText().toString().trim();
        String password = passwordButton.getText() == null ? "" : passwordButton.getText().toString().trim();

        if(username.isEmpty()) {
            usernameButton.setError("Please enter a username.");
        } else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            usernameButton.setError("Invalid email address.");
        } else if(password.isEmpty()) {
            usernameButton.setError("Please enter a password.");
        } else {
            onSuccess.accept(username, password);
        }
    }

}
