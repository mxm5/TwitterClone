package View;

import Base.View.View;
import Domain.User;
import Service.Services;

public class Register extends View {
    public Register() {
        String firstName = enterValue("enter your first name", 3);
        String lastName = enterValue("enter your last name", 3);
        String userName = enterValue("enter your personal user name", 3);

        String password;
        while (true) {
            password = enterValue("enter your your password", 3);
            String confirm = enterValue("confirm your password", 3);
            if (password.equals(confirm)) break;
        }

        User registered = new User(firstName, lastName, userName, password);
        try {
            Services.user.register(registered);
            success("register");
        } catch (Exception e) {
            e.printStackTrace();
            warning("register");
        }
        new Welcome();


    }
}
