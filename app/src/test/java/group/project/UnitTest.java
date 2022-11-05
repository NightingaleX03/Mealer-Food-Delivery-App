package group.project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import group.project.data.builder.CookBuilder;
import group.project.data.builder.UserBuilder;
import group.project.data.user.Cook;
import group.project.firebase.MemoryFireBuffer;


public class UnitTest {

    @Test
    public void test1() {
        CookBuilder builder = UserBuilder.ofCook()
                .setPrincipal("cook@mealer.com")
                .setPassword("cook")
                .setFirstName("Cook")
                .setLastName("Mealer")
                .setCity("Ottawa")
                .setHouseNumber(1000)
                .setProvince("ON")
                .setStreet("Street")
                .setDescription("This is a description.");

        Cook cook = builder.build();
        assertEquals(cook.getCredentials().getPrincipal(), "cook@mealer.com");
    }

    @Test
    public void test2() {
        CookBuilder builder = UserBuilder.ofCook()
                .setPrincipal("cook@mealer.com")
                .setPassword("cook")
                .setFirstName("Cook")
                .setLastName("Mealer")
                .setCity("Ottawa")
                .setHouseNumber(1000)
                .setProvince("ON")
                .setStreet("Street")
                .setDescription("This is a description.");

        Cook cook = builder.build();
        assertEquals(cook.getAddress().getCity(), "Ottawa");
    }

    @Test
    public void test3() {
        CookBuilder builder = UserBuilder.ofCook()
                .setPrincipal("cook@mealer.com")
                .setPassword("cook")
                .setFirstName("Cook")
                .setLastName("Mealer")
                .setCity("Ottawa")
                .setHouseNumber(1000)
                .setProvince("ON")
                .setStreet("Street")
                .setDescription("This is a description.");

        Cook cook = builder.build();
        assertEquals(cook.getAddress().getStreet(), "Street");
    }

    @Test
    public void test4() {
        MemoryFireBuffer buffer = MemoryFireBuffer.empty();
        buffer.writeInt("test", 2);
        assertEquals(buffer.toMap().get("test"), 2);
    }

}