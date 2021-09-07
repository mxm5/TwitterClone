package View;

import Base.View.View;
import Domain.FetchTweetsOf;
import Domain.User;
import Service.Services;

public class Dashboard extends View {
    public Dashboard() {
        int i = printOptions(
                "create a new tweet ",
                "search for users",
                "show all new tweets ",
                "view my profile"
        );
        int opt = selectOpt(i);
        switch (opt) {
            case 1 -> new PublishTweet();
            case 2 -> new SearchForUsers();
            case 3 -> new TweetList(FetchTweetsOf.AllUsers);
            case 4 -> new Profile(Services.getLoggedUser());
        }

    }
}
