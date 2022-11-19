package group.project;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

import group.project.data.Meal;
import group.project.data.user.Cook;
import group.project.data.user.User;
import group.project.firebase.FireDatabase;

public class CookHomePage extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list;

    Spinner spinnerAvailability;
    EditText itemName, mealtypes, cusinetypes, listOfIngredients, allergens, price, description;
    Button btnOK, addMenuItem;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        this.setContentView(R.layout.cook_home_page);

        addMenuItem = findViewById(R.id.addNewItem);
        list = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listview2);
        adapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String principal = list.get(position).trim().split(Pattern.quote("\n"))[0];
            if(principal.equals("There are no complaints.")) return;
            openDialog();
        });

        addMenuItem.setOnClickListener(view -> openDialog());

        User.getActive().ifPresent(user -> {
            Cook cook = (Cook)user;

            for(Meal meal : cook.getMeals()) {
                list.add("\n" + meal.toEntryString() + "\n");
            }

            System.out.println(list);
            adapter.notifyDataSetChanged();
        });

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });
    }

    private void openDialog() {
        System.out.println("create dialog");
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Menu Item");
        dialog.setContentView(R.layout.edit_menu_items);
        spinnerAvailability = dialog.findViewById(R.id.spinner_availability);
        btnOK = dialog.findViewById(R.id.btnsaveChanges);
        dialog.create();
        dialog.show();

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

            if(name.isEmpty()) {
                nameButton.setError("Please enter a name.");
            } else if(type.isEmpty()) {
                typeButton.setError("Please enter a type.");
            } else if(cuisine.isEmpty()) {
                cuisineButton.setError("Please enter a cuisine.");
            } else if(ingredients.isEmpty()) {
                ingredientsButton.setError("Please enter ingredients.");
            } else if(allergens.isEmpty()) {
                allergensButton.setError("Please enter allergens.");
            } else if(price.isEmpty()) {
                priceButton.setError("Please enter a price.");
            } else if(description.isEmpty()) {
                descriptionButton.setError("Please enter a description.");
            }

            Meal meal = new Meal(name, type, cuisine, ingredients, allergens, price, description);

            User.getActive().ifPresent(user -> {
                ((Cook)user).getMeals().add(meal);
                list.add("\n" + meal.toEntryString() + "\n");
                adapter.notifyDataSetChanged();
                FireDatabase.get().update(user);
                dialog.cancel();
            });
        });
    }

}
