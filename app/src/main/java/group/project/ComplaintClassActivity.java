package group.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ComplaintClassActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;
    Button btnSuspend, btnRevoke;
    TextView usernameComplaint;
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.complaint_class);

        listView = (ListView) findViewById(R.id.listview);
        btnSuspend = (Button) findViewById(R.id.btnsuspend);
        btnRevoke = (Button) findViewById(R.id.btnrevoke);
        usernameComplaint = (EditText) findViewById(R.id.usernameComplaint);

        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,list);

        btnRevoke.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String username = usernameComplaint.getText().toString();
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        btnSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
