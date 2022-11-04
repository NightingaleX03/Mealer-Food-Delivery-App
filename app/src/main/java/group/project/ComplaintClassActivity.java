package group.project;

import android.os.Bundle;
import android.widget.ListView
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ComplaintClassActivity extends AppCompatActivity {

    TextView usernameComplaint;
    TextView description;
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.complaint_class);
    }

    ListView listViewComplaint = findViewById(R.id.listview);

}
