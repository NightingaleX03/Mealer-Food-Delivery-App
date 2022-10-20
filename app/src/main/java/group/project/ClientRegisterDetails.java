package group.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ClientRegisterDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.register_client_details);

        this.findViewById(R.id.signUpAsClientButton).setOnClickListener(view -> {
            this.startActivity(new Intent(this, MainActivity.class));
        });
    }

}
