package org.example;

import builderExample.User;
import builderExample.UserBuilder;

public class Main {
    public static void main(String[] args) {
        //МЕТОД
        //URL
        //HEADERS
        //BODY
        //HttpClient httpClient = new HttpClientBuilder()

//        UserBuilder userBuilder = UserBuilder.create();
//        userBuilder.setCity("Seoul");
//        userBuilder.setSurname("Kim");
//        userBuilder.setName("RM");
//        User user = userBuilder.build();
//        System.out.println(user);

        User user = UserBuilder.create().setName("Jin").setCity("Seoul").setSurname("Kim").build();
        User user2 = UserBuilder.create().setName("RM").setCity("Seoul").setSurname("Kim").build();
        User user3 = UserBuilder.create().setName("V").setCity("Seoul").setSurname("Kim").build();
        System.out.println(user);
        System.out.println(user2);
        System.out.println(user3);
    }
}