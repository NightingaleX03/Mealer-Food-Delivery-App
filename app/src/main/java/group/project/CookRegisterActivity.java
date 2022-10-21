package group.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import group.project.data.builder.CookBuilder;
import group.project.data.builder.UserBuilder;
import group.project.firebase.FireDatabase;
import group.project.util.Consumer;

public class CookRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.register_cook);
        this.findViewById(R.id.signUpAsClient).setOnClickListener(view -> this.tryRegister());
    }

    public void tryRegister() {
        EditText firstNameButton = this.findViewById(R.id.firstName);
        EditText lastNameButton = this.findViewById(R.id.lastName);
        EditText emailAddressButton = this.findViewById(R.id.emailAddress);
        EditText passwordButton = this.findViewById(R.id.password);
        EditText password2Button = this.findViewById(R.id.password2);

        EditText houseNumberButton = this.findViewById(R.id.houseNumber);
        EditText streetNameButton = this.findViewById(R.id.StreetName);
        EditText cityButton = this.findViewById(R.id.City);
        EditText provinceButton = this.findViewById(R.id.province);
        EditText descriptionButton = this.findViewById(R.id.description);

        String firstName = firstNameButton.getText() == null ? "" : firstNameButton.getText().toString().trim();
        String lastName = lastNameButton.getText() == null ? "" : lastNameButton.getText().toString().trim();
        String email = emailAddressButton.getText() == null ? "" : emailAddressButton.getText().toString().trim();
        String password = passwordButton.getText() == null ? "" : passwordButton.getText().toString().trim();
        String password2 = password2Button.getText() == null ? "" : password2Button.getText().toString().trim();

        String streetName = streetNameButton.getText() == null ? "" : streetNameButton.getText().toString().trim();
        String city = cityButton.getText() == null ? "" : cityButton.getText().toString().trim();
        String province = provinceButton.getText() == null ? "" : provinceButton.getText().toString().trim();
        String description = descriptionButton.getText() == null ? "" : descriptionButton.getText().toString().trim();

        CookBuilder builder = UserBuilder.ofCook();

        if (firstName.isEmpty()) {
            firstNameButton.setError("Please enter a first name.");
        } else if(lastName.isEmpty()) {
            lastNameButton.setError("Please enter a last name.");
        } else if(email.isEmpty()) {
            lastNameButton.setError("Please enter a email.");
        } else if(password.isEmpty()) {
            passwordButton.setError("Please enter a password.");
        } else if(password2.isEmpty()) {
            password2Button.setError("Please enter a password.");
        } else if(!password.equals(password2)) {
            password2Button.setError("Passwords do not match up.");
        } else if(description.isEmpty()) {
            password2Button.setError("Please enter a description.");
        } else if(streetName.isEmpty()) {
            streetNameButton.setError("Please enter a street name.");
        } else if(!this.parseInt(houseNumberButton, builder::setHouseNumber)) {
            houseNumberButton.setError("Please enter a house number.");
        } else if(city.isEmpty()) {
            cityButton.setError("Please enter a city.");
        } else if(province.isEmpty()) {
            provinceButton.setError("Please enter a province.");
        } else {
            builder.setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPassword(password)
                    .setDescription(description)
                    .setStreet(streetName)
                    .setCity(city)
                    .setProvince(province);

            FireDatabase.get().register(builder,
                    user -> {
                        Toast.makeText(this, "Registered successfully! Please log in.", Toast.LENGTH_SHORT).show();
                        this.startActivity(new Intent(this, LoginActivity.class));
                    },
                    user -> {
                        Toast.makeText(this, "Invalid information, user already exists", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    public boolean parseInt(EditText value, Consumer<Integer> success) {
        if(value.getText() == null) return false;

        int parsed;

        try {
            parsed = Integer.parseInt(value.getText().toString());
        } catch(NumberFormatException e) {
            return false;
        }

        success.accept(parsed);
        return true;
    }

}
