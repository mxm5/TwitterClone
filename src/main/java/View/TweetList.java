package View;

import Base.View.View;
import Domain.FetchTweetsOf;
import Domain.Tweet;
import Service.Services;

import java.util.ArrayList;
import java.util.List;

public class TweetList extends View {
    FetchTweetsOf fetchTweetsOf;

    public TweetList(FetchTweetsOf fetchTweetsOf) {
        this.fetchTweetsOf = fetchTweetsOf;
        List<Tweet> chunk;
        int pageCount = 0;
        boolean browsing = true;
        while (browsing) {

            if (fetchTweetsOf == FetchTweetsOf.AllUsers)
                chunk = ((ArrayList<Tweet>) Services.tweet.loadTwentyRecentTweets(pageCount));
            else
                chunk = ((ArrayList<Tweet>) Services.tweet.loadUsersTwentyRecentTweets(pageCount));

            int k = 1;
            printTitle(pageCount + 1);
            line75();
            for (Tweet tweet : chunk) {
                print(k++);
                print("@ " + tweet.getOwner().getUserName());
                if (tweet.getTweetText().length() > 60)
                    print(tweet.getTweetText().substring(0, 60) + "...");
                else
                    print(tweet.getTweetText());
                print(sdf.format(tweet.getPublishDate()));
                line75();
            }

            int i = printOptions(
                    "show previous page",
                    "show next page",
                    "show tweet detail",
                    "find a user",
                    "go to dashboard",
                    "go to my profile"
            );
            int opt = selectOpt(i);
            switch (opt) {
                case 1 -> {
                    if (pageCount != 0) pageCount--;
                }
                case 2 -> {
                    if (chunk.size() != 0)
                        pageCount++;
                    else
                        print(" no other pages");
                }
                case 3 -> {
                    int opt1 = selectOpt(chunk.size());
                    Tweet tweet = chunk.get(opt1 - 1);
                    new TweetDetail(tweet);
                    browsing = false;
                }
                case 4 -> new SearchForUsers();
                case 5 -> new Dashboard();
                case 6 -> new Profile(Services.getLoggedUser());
                default -> throw new IllegalStateException("Unexpected value: " + opt);
            }
        }

    }
}
