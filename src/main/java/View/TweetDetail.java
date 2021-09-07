package View;

import Base.View.View;
import Domain.Comment;
import Domain.FetchTweetsOf;
import Domain.Tweet;
import Domain.User;
import Service.Services;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TweetDetail extends View {
    private Tweet tweet;
    private final List<Comment> comments;

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {


        this.tweet = tweet;
    }

    public TweetDetail(Tweet twt) {
        tweet = twt;
        comments = tweet.getComments().stream().toList();
        line75();
        print(tweet.getOwner().getFirstName());
        String s = formatTweet(tweet);
        String table = "|  published  |    likes    |   dislikes  |   comments  |";
        String row = String.format("|%-13s|%-13s|%-13s|%-13s|",
                StringUtils.center(tweet.getPublishDate().toString(), 13),
                StringUtils.center(String.valueOf(tweet.getLikedUsers().size()), 13),
                StringUtils.center(String.valueOf(tweet.getDislikedUsers().size()), 13),
                StringUtils.center(String.valueOf(tweet.getComments().size()), 13)
        );
        line75();
        print("@" + tweet.getOwner().getUserName());
        print();
        print(s);
        line75();
        print(table);
        print(row);
        line75();
        print("comments");
        comments.forEach(
                comment -> {
                    print("@ " + comment.getOwnerUser().getUserName());
                    if (comment.getCommentText().length() > 60)
                        print(comment.getCommentText().substring(0, 60) + "...");
                    else
                        print(comment.getCommentText());

                    print(sdf.format(comment.getPublishDate()));
                    line75();
                }
        );

        boolean browsing = true;
        boolean isOwner = Services.getLoggedUser() == tweet.getOwner();
        while (browsing) {


            List<String> list = new ArrayList<>(List.of(
                    "write a comment for this tweet",
                    "like this tweet",
                    "dislike this tweet",
                    "follow user",
                    "unfollow user",
                    "show all tweet list"
            ));
            if (isOwner)
                list.addAll(List.of("edit", "remove"));

            String[] strings = list.toArray(new String[0]);
            int i = printOptions(
                    strings
            );
            int opt = selectOpt(i);
            switch (opt) {
                case 1 -> {
                    new ReplyTo(tweet);
                    browsing = false;
                }
                case 2 -> {
                    User loggedUser = Services.getLoggedUser();
                    tweet.addLikeFromUser(loggedUser);
                    Services.tweet.save(tweet);
                    browsing = false;
                    new TweetDetail(tweet);

                }
                case 3 -> {
                    User loggedUser = Services.getLoggedUser();
                    tweet.addDislikeFromUser(loggedUser);
                    Services.tweet.save(tweet);
                    browsing = false;
                    new TweetDetail(tweet);
                }
                case 4 -> {
                    User owner = tweet.getOwner();
                    User loggedUser = Services.getLoggedUser();
                    owner.followedFromUser(loggedUser);
                    Services.user.save(owner);
                }
                case 5 -> {
                    User owner = tweet.getOwner();
                    User loggedUser = Services.getLoggedUser();
                    owner.unFollowedFromUser(loggedUser);
                    Services.user.save(owner);
                }
                case 6 -> {
                    new TweetList(FetchTweetsOf.AllUsers);
                    browsing = false;
                }
                case 7 -> {
                    if (!isOwner) print("invalid value");
                    else {
                        browsing = false;
                        new Edit(tweet);
                    }
                }
                case 8 -> {
                    if (!isOwner) print("invalid value");
                    else {
                        new ConfirmDelete(tweet);
                        browsing = false;
                    }

                }
            }
        }
    }


}
