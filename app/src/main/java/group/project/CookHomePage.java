package group.project;

import android.app.AppComponentFactory;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

import group.project.data.user.User;

public class CookHomePage extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list;

    Spinner spinnerAvailability;
    EditText itemName, mealtypes, cusinetypes, listOfIngredients, allergens, price, description;
    Button btnOK, addMenuItem;
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        this.setContentView(R.layout.cook_home_page);

        addMenuItem = findViewById(R.id.addNewItem);
        list = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listview2);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String principal = list.get(position).trim().split(Pattern.quote("\n"))[0];
            if(principal.equals("There are no complaints.")) return;
            openDialog();
        });

        addMenuItem.setOnClickListener(view ->
        {
            openDialog();
        });

        this.findViewById(R.id.logOut).setOnClickListener(view -> {
            User.setActive(null);
            this.onBackPressed();
        });
    }

    private void openDialog() {
        Dialog dialog = new Dialog(CookHomePage.this);
        dialog.setTitle("Menu Item");
        dialog.setContentView(R.layout.edit_menu_items);

        spinnerAvailability=findViewById(R.id.spinner_availability);
        itemName = findViewById(R.id.itemName);
        mealtypes = findViewById(R.id.mealType);
        cusinetypes = findViewById(R.id.cusineType);
        listOfIngredients = findViewById(R.id.listOfIngredients);
        allergens = findViewById(R.id.allergens);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        btnOK = findViewById(R.id.btnsaveChanges);


        
        btnOK.setOnClickListener(view ->
        {

        });

    }

}
