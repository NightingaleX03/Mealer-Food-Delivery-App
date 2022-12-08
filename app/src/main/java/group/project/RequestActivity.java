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
import java.util.regex.Pattern;

import group.project.data.Complaint;
import group.project.data.user.Cook;
import group.project.data.user.User;
import group.project.firebase.FireDatabase;

public class RequestActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;
    Button decline, accept;
    TextView clientRequest;
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.complaint_class);

        listView = (ListView) findViewById(R.id.listview);
        decline = (Button) findViewById(R.id.Declinebtn);
        accept = (Button) findViewById(R.id.Acceptbtn);
        clientRequest = (EditText) findViewById(R.id.ClientRequest);

        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String principal = list.get(position).trim().split(Pattern.quote("\n"))[0];
            if(principal.equals("There are no incoming requests.")) return;
            clientRequest.setText(principal);
        });

        // create list of requsts
        FireDatabase.get().getAllUsers(user -> {
            if(user instanceof Cook) {
                List<Request> requests = ((Cook)user).getRequest();

                for(Complaint complaint : requests) {
                    System.out.println(request.getClass());
                    String message = "\n" + user.getCredentials().getPrincipal() + "\n\n";
                    message += request.getContent();
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

        accept.setOnClickListener(view -> {
            String requestString = clientRequest.getText().toString();
            if(requestString.isEmpty()) return;

            FireDatabase.get().getUser(requestString.trim(), user -> {
                ((Cook)user).getRequest().clear();

                for(int i = list.size() - 1; i >= 0; i--) {
                    if(list.get(i).trim().startsWith(user.getCredentials().getPrincipal())) {
                        list.remove(i);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
                FireDatabase.get().update(user);
                Toast.makeText(this, "Successfully Accepted Order Request"
                        + user.getCredentials().getPrincipal() + ".", Toast.LENGTH_SHORT).show();
            });
        });

        decline.setOnClickListener(view -> {
            String requestString = clientRequest.getText().toString();
            if(requestString.isEmpty()) return;

            FireDatabase.get().getUser(requestString.trim(), user -> {
                ((Cook)user).getRequest().clear();

                for(int i = list.size() - 1; i >= 0; i--) {
                    if(list.get(i).trim().startsWith(user.getCredentials().getPrincipal())) {
                        list.remove(i);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
                FireDatabase.get().update(user);
                Toast.makeText(this, "Successfully Declined Order Request"
                        + user.getCredentials().getPrincipal() + ".", Toast.LENGTH_SHORT).show();
            });
        });


        this.findViewById(R.id.returnComplaints).setOnClickListener(view -> {
            this.setContentView(R.layout.complaint_class);
        });

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });
    }

}
