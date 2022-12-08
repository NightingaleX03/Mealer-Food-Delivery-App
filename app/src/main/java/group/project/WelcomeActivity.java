package group.project;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import group.project.data.Meal;
import group.project.data.user.Client;
import group.project.data.user.Cook;
import group.project.data.user.User;
import group.project.firebase.FireDatabase;

public class WelcomeActivity extends AppCompatActivity {

    private List<Entry> all = new ArrayList<>();
    private List<Entry> filtered = new ArrayList<>();

    private List<String> view = new ArrayList<String>() {
        @Override
        public String get(int index) {
            return WelcomeActivity.this.filtered.get(index).entry;
        }

        @Override
        public int size() {
            return WelcomeActivity.this.filtered.size();
        }
    };

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_activity);

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });

        ListView listView = (ListView)findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_list_item_1, this.view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            this.openDialog(position);
        });

        FireDatabase.get().getAllUsers(user -> {
            if(!(user instanceof Cook)) return;
            Cook cook = (Cook)user;

            for(Meal meal : cook.getMeals()) {
                if(!meal.isAvailable()) continue;
                this.all.add(new Entry(cook, meal, "\n" + meal.toEntryString() + "\n"));
            }
        }, () -> {
            this.applyFilter(((EditText)this.findViewById(R.id.searchBar)).getText().toString());
            adapter.notifyDataSetChanged();
        });

        this.findViewById(R.id.searchBar).setOnKeyListener((v, keyCode, event) -> {
            this.applyFilter(((EditText)v).getText().toString());
            adapter.notifyDataSetChanged();
            return false;
        });

        this.findViewById(R.id.btnsuspend).setOnClickListener(v -> {
            String text = ((TextView)this.findViewById(R.id.usernameComplaint)).getText().toString();
            if(!text.startsWith("Selected")) return;
            String value = text.split(Pattern.quote(":"))[1].trim();
            ((TextView)this.findViewById(R.id.usernameComplaint)).setText("Purchased: " + value);
        });

        User.getActive().ifPresent(user -> {
            TextView text = this.findViewById(R.id.welcome);

            text.setText(text.getText().toString()
                    .replaceAll(Pattern.quote("$firstName$"), ((Client)user).getFirstName()));
        });
    }

    public void applyFilter(String query) {
        this.filtered.clear();

        for(Entry entry : this.all) {
           if(entry.entry.toLowerCase().contains(query.trim().toLowerCase())) {
               this.filtered.add(entry);
            }
        }
    }

    private void openDialog(int index) {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Menu Item");
        dialog.setContentView(R.layout.edit_menu_items);

        Entry entry = this.filtered.get(index);
        AppCompatSpinner availability = dialog.findViewById(R.id.spinner_availability);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_item, Arrays.asList("Available", "Not Available"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availability.setAdapter(adapter);

        dialog.create();
        dialog.show();

        Meal meal = entry.meal;
        System.out.println(meal.toEntryString());
        ((EditText)dialog.findViewById(R.id.itemName)).setText(meal.getName());
        ((EditText)dialog.findViewById(R.id.mealType)).setText(meal.getType());
        ((EditText)dialog.findViewById(R.id.cusineType)).setText(meal.getCuisine());
        ((EditText)dialog.findViewById(R.id.listOfIngredients)).setText(meal.getIngredients());
        ((EditText)dialog.findViewById(R.id.allergens)).setText(meal.getAllergens());
        ((EditText)dialog.findViewById(R.id.price)).setText(meal.getPrice());
        ((EditText)dialog.findViewById(R.id.description)).setText(meal.getDescription());
        availability.setSelection(meal.isAvailable() ? 0 : 1);

        ((Button)dialog.findViewById(R.id.btnsaveChanges)).setText("Select");

        dialog.findViewById(R.id.btnsaveChanges).setOnClickListener(v -> {
            ((TextView)this.findViewById(R.id.usernameComplaint)).setText("Selected: " + entry.meal.getName());
            dialog.cancel();
        });
    }

    public static class Entry {
        public Cook cook;
        public Meal meal;
        public String entry;

        public Entry(Cook cook, Meal meal, String entry) {
            this.cook = cook;
            this.meal = meal;
            this.entry = entry;
        }
    }

}
