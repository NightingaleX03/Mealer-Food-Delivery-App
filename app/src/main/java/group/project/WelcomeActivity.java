package group.project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import group.project.data.user.User;

public class WelcomeActivity extends AppCompatActivity {

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_activity);

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });

        User.getActive().ifPresent(user -> {
            if(user == null) return;
            TextView text = this.findViewById(R.id.welcomeMessage);
            text.setText(text.getText().toString()
                    .replaceAll(Pattern.quote("$user_type$"), user.getType().name()));
        });
    }

}
