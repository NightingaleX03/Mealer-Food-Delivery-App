package group.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CookRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.register_cook);

        this.findViewById(R.id.signUpAsClient).setOnClickListener(view -> {
            this.startActivity(new Intent(this, MainActivity.class));
        });
    }

}
