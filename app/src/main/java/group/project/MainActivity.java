package group.project;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);

        this.firestore = FirebaseFirestore.getInstance();

        Map<String, Object> values = new LinkedHashMap<>();
        values.put("integer", 5);
        values.put("string", "test");
        values.put("double", Math.PI);

        this.firestore.collection("test").add(values).addOnSuccessListener(command ->
                Toast.makeText(this.getApplicationContext(), "Database Magic Successful!", Toast.LENGTH_LONG).show());
    }

}
