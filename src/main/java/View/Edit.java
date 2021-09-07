package View;

import Base.View.View;
import Domain.FetchTweetsOf;
import Domain.Tweet;
import Service.Services;

public class Edit extends View {
    Tweet twt;

    public Edit(Tweet tweet) {
        this.twt = tweet;
        String tweetText = enterLine280("insert your edited message here");
        twt.setTweetText(tweetText);
        Services.tweet.save(twt);
        new TweetDetail(twt);
    }
}
