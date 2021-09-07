package Tests;

import Domain.Comment;
import Domain.Tweet;
import Domain.User;
import Service.Services;

import java.util.Optional;

public class TestC_comment_owner {
    public static void main(String[] args) {
        Optional<Tweet> byId = Services.tweet.getById(13L);
        Tweet tweet = byId.get();
        Comment comment = FakeData.fakeComment();
        Optional<User> byId1 = Services.user.getById(1L);
        User user = byId1.get();
        comment.setOwnerUser(user);
        comment.setInReplyToTweet(tweet);
        Services.comment.save(comment);
        tweet.getComments().forEach(System.out::println);
        System.out.println(tweet.getComments().size());
//        tweet.getComments().add(comment);
//        Services.tweet.save(tweet);

    }
}
