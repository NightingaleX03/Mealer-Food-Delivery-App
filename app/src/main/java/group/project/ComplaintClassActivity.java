package group.project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import group.project.data.Complaint;
import group.project.data.user.Cook;
import group.project.data.user.User;
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
            String principal = list.get(position).trim().split(Pattern.quote("\n"))[0];
            if(principal.equals("There are no complaints.")) return;
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
            }
        }, () -> {
            if(list.isEmpty()) {
                list.add("\nThere are no complaints.\n");
            }

            arrayAdapter.notifyDataSetChanged();
        });

        btnRevoke.setOnClickListener(view -> {
            String username = usernameComplaint.getText().toString();
            if(username.isEmpty()) return;

            FireDatabase.get().getUser(username.trim(), user -> {
                ((Cook)user).getComplaints().clear();

                for(int i = list.size() - 1; i >= 0; i--) {
                    if(list.get(i).trim().startsWith(user.getCredentials().getPrincipal())) {
                        list.remove(i);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
                FireDatabase.get().update(user);
                Toast.makeText(this, "Successfully revoked complaint on "
                        + user.getCredentials().getPrincipal() + ".", Toast.LENGTH_SHORT).show();
            });
        });

        btnSuspend.setOnClickListener(view -> {
            String username = usernameComplaint.getText().toString();
            if(username.isEmpty()) return;

            FireDatabase.get().getUser(username.trim(), user -> {
                ((Cook)user).getComplaints().clear();
                ((Cook)user).setSuspensionDays(10);

                for(int i = list.size() - 1; i >= 0; i--) {
                    if(list.get(i).trim().startsWith(user.getCredentials().getPrincipal())) {
                        list.remove(i);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
                FireDatabase.get().update(user);
                Toast.makeText(this, "Successfully suspended " + user.getCredentials().getPrincipal()
                        + " for 10 days.", Toast.LENGTH_SHORT).show();
            });
        });

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });
    }
}
