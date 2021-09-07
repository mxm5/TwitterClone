package Service;

import Domain.User;
import Repository.CommentRepository;
import Repository.TweetRepository;
import Repository.UserRepository;

public class Services {

    public static CommentService comment;
    public static TweetService tweet;
    public static UserService user;
    public static User loggedUser;

    public static User getLoggedUser() {

        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        Services.loggedUser = loggedUser;
    }

    static {
        comment = new CommentService(new CommentRepository());
        tweet = new TweetService(new TweetRepository());
        user = new UserService(new UserRepository());
    }

}
