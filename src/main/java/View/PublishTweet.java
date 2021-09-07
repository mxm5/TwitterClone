package View;

import Base.View.View;
import Domain.Tweet;
import Domain.User;
import Service.Services;

public class PublishTweet extends View {
    public PublishTweet() {
        String tweetText = enterLine280("enter the tweet ");
        Tweet tweet = new Tweet(tweetText);
        User loggedUser = Services.getLoggedUser();
        tweet.setOwner(loggedUser);
        Services.tweet.save(tweet);
        loggedUser.getPublishedTweets().add(tweet);
        new Profile(loggedUser);

    }
}
