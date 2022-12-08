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
import java.util.regex.Pattern;

import group.project.data.Meal;
import group.project.data.user.Cook;
import group.project.data.user.User;
import group.project.firebase.FireDatabase;

public class WelcomeActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list;
    Button purchaseBtn;
    TextView status_pending;
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_activity);

        listView = (ListView) findViewById(R.id.listview);
        purchaseBtn = (Button) findViewById(R.id.btnsuspend);
        status_pending = (EditText) findViewById(R.id.usernameComplaint);

        list = new ArrayList<>();

        // need to enter list of all the menus for every cook
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            openDialog(position);
        });

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });

        // need to display username first name on welcome screen
        User.getActive().ifPresent(user -> {
            if(user == null) return;
            TextView text = this.findViewById(R.id.welcome);
            text.setText(text.getText().toString()
                    .replaceAll(Pattern.quote("$firstName$"), user.getType().name()));
        });
    }
        private void openDialog (int index) {
            Dialog dialog = new Dialog(this);
            dialog.setTitle("Menu Item");
            dialog.setContentView(R.layout.edit_menu_items);


            AppCompatSpinner availability = dialog.findViewById(R.id.spinner_availability);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_item, Arrays.asList("Available", "Not Available"));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            availability.setAdapter(adapter);

            dialog.create();
            dialog.show();



                // need to change text of button to 'Purchase'
            dialog.findViewById(R.id.btnsaveChanges).setOnClickListener(v -> {
                EditText nameButton = dialog.findViewById(R.id.itemName);
                EditText typeButton = dialog.findViewById(R.id.mealType);
                EditText cuisineButton = dialog.findViewById(R.id.cusineType);
                EditText ingredientsButton = dialog.findViewById(R.id.listOfIngredients);
                EditText allergensButton = dialog.findViewById(R.id.allergens);
                EditText priceButton = dialog.findViewById(R.id.price);
                EditText descriptionButton = dialog.findViewById(R.id.description);

                String name = nameButton.getText() == null ? "" : nameButton.getText().toString().trim();
                String type = typeButton.getText() == null ? "" : typeButton.getText().toString().trim();
                String cuisine = cuisineButton.getText() == null ? "" : cuisineButton.getText().toString().trim();
                String ingredients = ingredientsButton.getText() == null ? "" : ingredientsButton.getText().toString().trim();
                String allergens = allergensButton.getText() == null ? "" : allergensButton.getText().toString().trim();
                String price = priceButton.getText() == null ? "" : priceButton.getText().toString().trim();
                String description = descriptionButton.getText() == null ? "" : descriptionButton.getText().toString().trim();

                Meal meal = new Meal(name, type, cuisine, ingredients, allergens, price, description, availability.getSelectedItemPosition() == 0);

                User.getActive().ifPresent(user -> {
                    Cook cook = (Cook)user;

                    if(index >= 0 && index < cook.getMeals().size()) {
                        cook.getMeals().set(index, meal);
                        list.set(index, "\n" + meal.toEntryString() + "\n");
                    } else {
                        cook.getMeals().add(meal);
                        list.add("\n" + meal.toEntryString() + "\n");
                    }
                });
            });

            User.getActive().ifPresent(user -> {
                Cook cook = (Cook)user;

                if(index >= 0 && index < cook.getMeals().size()) {
                    Meal meal = cook.getMeals().get(index);
                    System.out.println(meal.toEntryString());
                    ((EditText)dialog.findViewById(R.id.itemName)).setText(meal.getName());
                    ((EditText)dialog.findViewById(R.id.mealType)).setText(meal.getType());
                    ((EditText)dialog.findViewById(R.id.cusineType)).setText(meal.getCuisine());
                    ((EditText)dialog.findViewById(R.id.listOfIngredients)).setText(meal.getIngredients());
                    ((EditText)dialog.findViewById(R.id.allergens)).setText(meal.getAllergens());
                    ((EditText)dialog.findViewById(R.id.price)).setText(meal.getPrice());
                    ((EditText)dialog.findViewById(R.id.description)).setText(meal.getDescription());
                    availability.setSelection(meal.isAvailable() ? 0 : 1);
                }
            });
        }

        // need to add method to change text for

        // need method for search bar

    }
