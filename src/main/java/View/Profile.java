package View;


import Base.View.View;
import Domain.FetchTweetsOf;
import Domain.User;
import Service.Services;


public class Profile extends View {
    private User profileOwner;


    public Profile(User user) {
        printTitle("@ " + user.getUserName());

        Long id = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        int followers = user.getFollowers().size();
        int following = user.getFollowing().size();
        profileOwner = user;
        line75();
        print("user id :" + id);
        print("first name :" + firstName);
        print("last name :" + lastName);
        print("followers :" + followers);
        print("following :" + following);
        line75();
        if (Services.getLoggedUser() == profileOwner) {

            printOptions(
                    "create new tweet",
                    "view my tweets"
                    /*"search for user"*/
                    /*"view my comments"*/);
            int opt = selectOpt(2);
            switch (opt) {
                case 1 -> new PublishTweet();
                case 2 -> new TweetList(FetchTweetsOf.CurrentUser);
            }
        } else {
            int i = printOptions("go to tweet list", "follow this user", "unfollow user", "search again");
            int opt = selectOpt(i);
            switch (opt) {
                case 1 -> new TweetList(FetchTweetsOf.AllUsers);
                case 2 -> {
                    User owner = profileOwner;
                    User loggedUser = Services.getLoggedUser();
                    owner.followedFromUser(loggedUser);
                    Services.user.save(owner);
                    new Profile(owner);
                }
                case 3 -> {
                    User owner = profileOwner;
                    User loggedUser = Services.getLoggedUser();
                    owner.unFollowedFromUser(loggedUser);
                    Services.user.save(owner);
                    new Profile(owner);
                }
                case 4 -> new SearchForUsers();
            }

        }

    }
}
