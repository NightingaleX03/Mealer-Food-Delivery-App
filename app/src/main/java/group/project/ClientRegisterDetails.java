package group.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;

import group.project.firebase.FireDatabase;

public class ClientRegisterDetails extends AppCompatActivity {
    private FireDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.register_client_details);

        this.database = new FireDatabase(FirebaseFirestore.getInstance());


        this.findViewById(R.id.signUpAsClientButton).setOnClickListener(view -> {
            this.startActivity(new Intent(this, MainActivity.class));
        });

    }

}
