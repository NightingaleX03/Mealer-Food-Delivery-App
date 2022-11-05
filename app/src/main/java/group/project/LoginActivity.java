package group.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import group.project.data.user.Admin;
import group.project.data.user.Cook;
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
                            User.setActive(user);

                            if(user instanceof Cook && ((Cook)user).getSuspensionDays() > 0) {
                                this.startActivity(new Intent(this, CookSuspendActivity.class));
                            } else if(user instanceof Admin) {
                                this.startActivity(new Intent(this, ComplaintClassActivity.class));
                            } else {
                                this.startActivity(new Intent(this, WelcomeActivity.class));
                            }

                            ((EditText)this.findViewById(R.id.password)).setText("");
                        },
                        user -> {
                            ((EditText)this.findViewById(R.id.username)).setError("Invalid credentials.");
                            ((EditText)this.findViewById(R.id.password)).setError("Invalid credentials.");
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
    }

    public void tryLogin(BiConsumer<String, String> onSuccess) {
        EditText usernameButton = this.findViewById(R.id.username);
        EditText passwordButton = this.findViewById(R.id.password);
        String username = usernameButton.getText() == null ? "" : usernameButton.getText().toString().trim();
        String password = passwordButton.getText() == null ? "" : passwordButton.getText().toString().trim();

        if(username.isEmpty()) {
            usernameButton.setError("Please enter a username.");
        } else if(password.isEmpty()) {
            usernameButton.setError("Please enter a password.");
        } else {
            onSuccess.accept(username, password);
        }
    }

}
