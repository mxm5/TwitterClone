package Tests;

import Domain.Comment;
import Domain.Tweet;
import Domain.User;
import Service.Services;
import Service.TweetService;

import java.util.Optional;
import java.util.stream.IntStream;

public class TestD_safeRemoveTweet {

    public static void main(String[] args) {
//        createATestUserWithATestTweet();
        Optional<User> byId = Services.user.getById(130L);
        System.out.println(byId.get());
        Tweet tweet = FakeData.fakeTweet();
        tweet.setOwner(byId.get());
        Services.tweet.save(tweet);
        System.out.println(tweet.getId());
//        Optional<Tweet> byId = Services.tweet.getById(119L);
//        Services.tweet.delete(byId.get());
//         createFiveUsersCommentingOn119Tweet();

    }

    private static void createFiveUsersCommentingOn119Tweet() {
        IntStream.range(0, 5).forEach(
                value -> {
                    User user = FakeData.fakeUser();
                    Services.user.save(user);
                    System.out.println("user id : " + user.getId());
                    Comment comment = FakeData.fakeComment();
                    comment.setOwnerUser(user);
                    Tweet tweet = Services.tweet.getById(131L).get();
                    tweet.addLikeFromUser(user);
                    Services.tweet.save(tweet);
                    Services.comment.save(comment);
                    System.out.println("comment id :" + comment.getId());
//                    120 122 124 156 128 owners must remain
//                    121 123 125 127 129 comments must be deleted

                }
        );
    }

    private static void createATestUserWithATestTweet() {
        Tweet tweet = FakeData.fakeTweet();
        User user = FakeData.fakeUser();
        Services.user.save(user);   /*130*/
        tweet.setOwner(user);
        Services.tweet.save(tweet); /*131*/
        System.out.println(tweet.getId());
        System.out.println(tweet);
        System.out.println(user.getId());
        System.out.println(user);
    }

}
