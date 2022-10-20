package group.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.firestore.FirebaseFirestore;

import group.project.data.Address;
import group.project.data.PaymentInfo;
import group.project.data.builder.ClientBuilder;
import group.project.data.builder.UserBuilder;
import group.project.data.user.User;
import group.project.firebase.FireDatabase;
import group.project.util.Consumer;

    public class MainActivity extends AppCompatActivity {

    private FireDatabase database;

    Button clientSignUp , cookSignUp;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);

        this.database = new FireDatabase(FirebaseFirestore.getInstance());

        this.findViewById(R.id.loginbtn).setOnClickListener(view -> {
            AppCompatEditText usernameButton = this.findViewById(R.id.username);
            AppCompatEditText passwordButton = this.findViewById(R.id.password);
            String username = usernameButton.getText() == null ? "" : usernameButton.getText().toString().trim();
            String password = passwordButton.getText() == null ? "" : passwordButton.getText().toString().trim();
            boolean validInput = true;

            if(username.isEmpty()) {
                usernameButton.setError("Please enter a username.");
                validInput = false;
            } else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                usernameButton.setError("Invalid email address.");
                validInput = false;
            }

            if(password.isEmpty()) {
                usernameButton.setError("Please enter a password.");
                validInput = false;
            }

            if(!validInput) return;

            this.login(username, password,
                    user -> {
                        this.startActivity(new Intent(this, WelcomeActivity.class));
                    },
                    user -> {
                        usernameButton.setError("Invalid username.");
                        passwordButton.setError("Invalid password.");
                        Toast.makeText(this, "Invalid credentials, please try again", Toast.LENGTH_SHORT).show();
                    });
        });

        this.findViewById(R.id.signUpAsClient).setOnClickListener(view -> {
            this.startActivity(new Intent(this, ClientRegisterDetails.class));
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

        this.register(client,
                user -> {
                    System.out.println("User registered successfully! ============================");
                },
                user -> {
                    System.out.println("User already exists. ============================");
                });

        clientSignUp=(Button)findViewById(R.id.signUpAsClient);
        cookSignUp = (Button)findViewById(R.id.signUpAsCook);

        clientSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentClient = new Intent(MainActivity.this, ClientRegisterDetails.class);
                startActivity(intentClient);
            }
        });

        cookSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCook = new Intent(MainActivity.this, CookRegisterActivity.class);
                startActivity(intentCook);
            }
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
