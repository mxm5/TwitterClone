package View;

import Base.View.View;
import Domain.FetchTweetsOf;
import Domain.User;
import Service.Services;

import java.util.List;

public class SearchForUsers extends View {
    public SearchForUsers() {
        String query = enterValue("please insert your search query here");
        List<User> userByUserNameLike = Services.user.findUserByUserNameLike(query);
        int k = 1;

        line75();
        if (userByUserNameLike.size() != 0) {
            for (User user : userByUserNameLike) {
                print(k++ + "@ " + user.getUserName());
                line75();
            }
        } else
            print("no results found");
        line75();
        printOptions("show profile of user  ", "show list of tweets", "search again");
//        new TweetList(FetchTweetsOf.AllUsers);
        int opt = selectOpt(3);
        switch (opt) {
            case 1 -> {
                int opt1 = selectOpt(userByUserNameLike.size());
                User user = userByUserNameLike.get(opt1 - 1);
                new Profile(user);
            }
            case 2 -> new TweetList(FetchTweetsOf.AllUsers);
            case 3 -> new SearchForUsers();
        }
    }
}
