package Tests;

import Domain.Tweet;
import Domain.User;
import Service.Services;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Set;

public class TestB_tweetLike {
    public static void main(String[] args) {
        Optional<Tweet> byId = Services.tweet.getById(3L);
        Tweet tweet = byId.get();
        System.out.println(tweet.getLikedUsers().size() + " like");
        System.out.println(tweet.getDislikedUsers().size() + " dislike");
        Optional<User> byId1 = Services.user.getById(2L);
        User user = byId1.get();
        tweet.addLikeFromUser(user);
//        Services.tweet.save(tweet);
//        boolean b = tweet.removeLikeFromUser(user);
//        Set<User> likedUsers = tweet.getLikedUsers();
//        boolean contains = likedUsers.contains(user);
//
//        System.out.println("contains " + contains);
//        System.out.println(user);
//        System.out.println(likedUsers);
//        System.out.println(b);
//        Services.tweet.save(tweet);

    }
}
