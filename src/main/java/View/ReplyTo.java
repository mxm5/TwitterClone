package View;

import Base.View.View;
import Domain.Comment;
import Domain.Tweet;
import Service.Services;

import java.util.Optional;

public class ReplyTo extends View {
    public ReplyTo(Tweet tweet) {

        String commentText = enterLine280("enter your comment text ");
        Comment comment = new Comment(commentText, tweet, Services.getLoggedUser());
        Services.comment.save(comment);
        tweet.getComments().add(comment);
        new TweetDetail(tweet);
    }
}
