package group.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import group.project.data.builder.ClientBuilder;
import group.project.data.builder.UserBuilder;
import group.project.firebase.FireDatabase;
import group.project.util.Consumer;

public class ClientRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.register_client_details);
        this.findViewById(R.id.signUpAsClientButton).setOnClickListener(view -> this.tryRegister());
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

        EditText cardNUmberButton = this.findViewById(R.id.cardNumber);
        EditText monthEXPButton = this.findViewById(R.id.monthEXP);
        EditText YearEXPButton = this.findViewById(R.id.yearEXP);
        EditText CVCButton = this.findViewById(R.id.CVC);

        String firstName = firstNameButton.getText() == null ? "" : firstNameButton.getText().toString().trim();
        String lastName = lastNameButton.getText() == null ? "" : lastNameButton.getText().toString().trim();
        String email = emailAddressButton.getText() == null ? "" : emailAddressButton.getText().toString().trim();
        String password = passwordButton.getText() == null ? "" : passwordButton.getText().toString().trim();
        String password2 = password2Button.getText() == null ? "" : password2Button.getText().toString().trim();

        String streetName = streetNameButton.getText() == null ? "" : streetNameButton.getText().toString().trim();
        String city = cityButton.getText() == null ? "" : cityButton.getText().toString().trim();
        String province = provinceButton.getText() == null ? "" : provinceButton.getText().toString().trim();

        String cardNumber = cardNUmberButton.getText() == null ? "" : cardNUmberButton.getText().toString().trim();

        ClientBuilder builder = UserBuilder.ofClient();

        if (firstName.isEmpty()) {
            firstNameButton.setError("Please enter a first name.");
        } else if(lastName.isEmpty()) {
            lastNameButton.setError("Please enter a last name.");
        } else if(email.isEmpty()) {
            emailAddressButton.setError("Please enter an email.");
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddressButton.setError("Invalid email.");
        } else if(password.isEmpty()) {
            passwordButton.setError("Please enter a password.");
        } else if(password.length() < 5) {
            passwordButton.setError("Password must be at least 5 characters long.");
        } else if(password2.isEmpty()) {
            password2Button.setError("Please enter a password.");
        } else if(!password.equals(password2)) {
            password2Button.setError("Passwords do not match up.");
        }

        else if(cardNumber.isEmpty()) {
            cardNUmberButton.setError("Please enter a card number.");
        } else if(!this.parseInt(monthEXPButton, builder::setMonthExpiration)) {
            monthEXPButton.setError("Please enter a month EXP.");
        }else if(!this.parseInt(YearEXPButton, builder::setYearExpiration)) {
            YearEXPButton.setError("Please enter a year EXP.");
        } else if(!this.parseInt(CVCButton, builder::setVerificationValue)) {
            CVCButton.setError("Please enter a CVC.");
        }

        else if(!this.parseInt(houseNumberButton, builder::setHouseNumber)) {
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
                    .setStreet(streetName)
                    .setCity(city)
                    .setProvince(province)
                    .setCardNumber(cardNumber);

            FireDatabase.get().register(builder,
                    user -> {
                        Toast.makeText(this, "Registered successfully! Please log in.", Toast.LENGTH_SHORT).show();
                        this.startActivity(new Intent(this, LoginActivity.class));
                    },
                    user -> {
                        Toast.makeText(this, "Invalid information, user already exists.", Toast.LENGTH_SHORT).show();
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
