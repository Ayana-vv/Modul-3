package builderExample;

public class UserBuilder {
    private static User user;

    public static UserBuilder create() {
        user = new User();
        return new UserBuilder();
    }

    public UserBuilder setName(String name) {
        user.setName(name);
        return this;
    }
    public UserBuilder setSurname(String surname) {
        user.setSurname(surname);
        return this;
    }
    public UserBuilder setCity(String city) {
        user.setCity(city);
        return this;
    }
    public User build() {
        return user;
    }
}
