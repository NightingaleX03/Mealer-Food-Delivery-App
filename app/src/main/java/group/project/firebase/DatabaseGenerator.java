package group.project.firebase;

import android.annotation.SuppressLint;

import group.project.data.builder.AdminBuilder;
import group.project.data.builder.CookBuilder;
import group.project.data.builder.UserBuilder;

@SuppressLint("DefaultLocale")
public class DatabaseGenerator {

    // This needs to be called from a proper android context.
    public static void invoke() {
        for(int i = 1; i <= 5; i++) {
            registerGenericAdmin(i);
        }

        for(int i = 1; i <= 9; i++) {
            registerGenericCook(i);
        }
    }

    private static void registerGenericAdmin(int index) {
        AdminBuilder builder = UserBuilder.ofAdmin()
                .setPrincipal(String.format("admin%d@mealer.com", index))
                .setPassword(String.format("admin%d", index));

        FireDatabase.get().register(builder,
                user -> {
                    System.out.printf("[%s] Registered successfully!%n", user.getCredentials().getPrincipal());
                },
                user -> {
                    System.out.printf("[%s] User already exists. %n", user.getCredentials().getPrincipal());
                });
    }

    private static void registerGenericCook(int index) {
        CookBuilder builder = UserBuilder.ofCook()
                .setPrincipal(String.format("cook%d@mealer.com", index))
                .setPassword(String.format("cook%d", index))
                .setFirstName("Cook" + index)
                .setLastName("Mealer")
                .setCity("Ottawa")
                .setHouseNumber(1000 + index)
                .setProvince("ON")
                .setStreet("Street" + index)
                .setDescription("This is a description.");

        FireDatabase.get().register(builder,
                user -> {
                    System.out.printf("[%s] Registered successfully!%n", user.getCredentials().getPrincipal());
                },
                user -> {
                    System.out.printf("[%s] User already exists. %n", user.getCredentials().getPrincipal());
                });
    }

}
