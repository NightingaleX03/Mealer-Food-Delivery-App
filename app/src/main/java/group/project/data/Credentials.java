package group.project.data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import group.project.firebase.FireBuffer;
import group.project.firebase.IFireSerializable;

public final class Credentials implements IFireSerializable {

    private static final String SALT_SPACE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private String principal;
    private String passwordHash;
    private String passwordSalt;

    private Credentials() {

    }

    private Credentials(String principal, String passwordHash, String passwordSalt) {
        this.principal = principal;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public static Credentials create(String principal, String password) {
        String salt = generateSalt(new Random(), 16);
        return new Credentials(principal, hash(password + salt), salt);
    }

    public static Credentials fromBuffer(FireBuffer buffer) {
        Credentials credentials = new Credentials();
        credentials.read(buffer);
        return credentials;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public boolean canAuthenticate(String principal, String password) {
        return this.principal.equals(principal)
                && this.passwordHash.equals(hash(password + this.passwordSalt));
    }

    @Override
    public void write(FireBuffer buffer) {
        buffer.writeString("principal", this.principal);
        buffer.writeString("password_hash", this.passwordHash);
        buffer.writeString("password_salt", this.passwordSalt);
    }

    @Override
    public void read(FireBuffer buffer) {
        this.principal = buffer.readString("principal");
        this.passwordHash = buffer.readString("password_hash");
        this.passwordSalt = buffer.readString("password_salt");
    }

    private static String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return password;
    }

    private static String generateSalt(Random random, int size) {
        StringBuilder salt = new StringBuilder();

        for(int i = 0; i < size; i++) {
            salt.append(SALT_SPACE.charAt(random.nextInt(SALT_SPACE.length())));
        }

        return salt.toString();
    }

}
