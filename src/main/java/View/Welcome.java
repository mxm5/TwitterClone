package View;

import Base.View.View;

public class Welcome extends View {
    public Welcome() {
        int i = printOptions("login", "register", "exit");
        int opt = selectOpt(i);
        switch (opt) {
            case 1 -> new Login();
            case 2 -> new Register();
            case 3 -> {
                printTitle("good bye");
                print("exiting ...");
            }
        }

    }
}
