package group.project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import group.project.data.Complaint;
import group.project.data.user.Cook;
import group.project.firebase.FireDatabase;

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
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String principal = list.get(position).trim().split(Pattern.quote(" "))[0];
            usernameComplaint.setText(principal);
        });

        FireDatabase.get().getAllUsers(user -> {
            if(user instanceof Cook) {
                List<Complaint> complaints = ((Cook)user).getComplaints();

                for(Complaint complaint : complaints) {
                    System.out.println(complaint.getClass());
                    String message = "\n" + user.getCredentials().getPrincipal() + "\n\n";
                    message += complaint.getContent();
                    message += "\n\n";
                    list.add(message);
                }

                arrayAdapter.notifyDataSetChanged();
            }
        });

        btnRevoke.setOnClickListener(view -> {
            String username = usernameComplaint.getText().toString();
        });

        btnSuspend.setOnClickListener(view -> {

        });
    }
}
