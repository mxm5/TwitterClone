package View;

import Base.View.View;
import Domain.FetchTweetsOf;
import Domain.Tweet;
import Service.Services;

public class ConfirmDelete extends View {
    Tweet twt;

    public ConfirmDelete(Tweet tweet) {
        this.twt = tweet;
        printOptions("delete tweet", "cancel");
        int opt = selectOpt(2);
        if (opt == 1)
            Services.tweet.delete(twt);
        new TweetList(FetchTweetsOf.AllUsers);

    }
}
