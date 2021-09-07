package View;

import Base.View.View;
import Domain.User;
import Service.Services;

public class Login extends View {
    public Login() {
        {

            String username = enterValue("enter your user name", 3);

            String password = enterValue("enter your password", 3);

            User enteredUser = new User(username, password);

            User login = Services.user.login(enteredUser);

            if (login != null) {
                success("login successful");
                Services.setLoggedUser(login);
                new Dashboard();


            } else {
                warning("wrong information");
                new Welcome();
            }

        }
    }
}
