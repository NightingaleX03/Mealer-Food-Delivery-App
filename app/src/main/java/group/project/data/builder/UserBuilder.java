package group.project.data.builder;

import group.project.data.user.User;

public abstract class UserBuilder<B extends UserBuilder<B, U>, U extends User> {

    protected String principal;
    protected String password;

    protected UserBuilder() {

    }

    public static ClientBuilder ofClient() {
        return new ClientBuilder();
    }

    public static CookBuilder ofCook() {
        return new CookBuilder();
    }

    public static AdminBuilder ofAdmin() {
        return new AdminBuilder();
    }

    public B setPrincipal(String principal) {
        this.principal = principal;
        return (B)this;
    }

    public B setPassword(String password) {
        this.password = password;
        return (B)this;
    }

    public abstract U build();

}
