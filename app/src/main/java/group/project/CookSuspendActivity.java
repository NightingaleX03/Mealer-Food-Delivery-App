package group.project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import group.project.data.user.Cook;
import group.project.data.user.User;

public class CookSuspendActivity extends AppCompatActivity {

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.cook_suspend);

        /*
        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });*/

        User.getActive().ifPresent(user -> {
            if(user == null) return;
            TextView text = this.findViewById(R.id.suspend_duration);

            if(user instanceof Cook) {
                Cook cook = (Cook)user;

                text.setText(text.getText().toString()
                        .replaceAll(Pattern.quote("$days_suspended$"), cook.getSuspensionDays() + ""));
            }
        });

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });
    }

}
