package group.project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import group.project.data.Order;
import group.project.data.user.Cook;
import group.project.data.user.User;

public class RequestActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.complaint_class);

        listView = (ListView) findViewById(R.id.listview);
        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        User.getActive().ifPresent(user -> {
            if(!(user instanceof Cook)) return;
            Cook cook = (Cook)user;

            for(Order order : cook.getOrders()) {
                list.add(order.getContent());
            }
        });

        if(list.isEmpty()) {
            list.add("\nThere are no active orders.\n");
        }

        arrayAdapter.notifyDataSetChanged();

        this.findViewById(R.id.returnComplaints).setOnClickListener(view -> {
            this.onBackPressed();
        });

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
            this.onBackPressed();
        });
    }

}
