package Tests;

import Domain.Comment;
import Domain.Tweet;
import Domain.User;
import Service.Services;
import com.github.javafaker.Faker;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FakeData {
    public static Faker faker = new Faker();

    public static void main(String[] args) {
        User user = Services.user.getById(2L).get();
        Services.tweet.getAll().forEach(
                tweet -> {
                    String tweetText = tweet.getTweetText();
                    String s = tweetText.replaceAll("]", "");
                    String s1 = s.replaceAll("\\[", "");
                    tweet.setTweetText(s1);
                    tweet.setOwner(user);
                    Services.tweet.save(tweet);
                }

        );
        user.getPublishedTweets().forEach(System.out::println);

        Services.user.save(user);
    }

    public static User fakeUser() {
        return new User(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                "12345"
        );
    }

    public static Comment fakeComment() {
        return new Comment(
                String.join(" ", faker.lorem().paragraphs(4)).substring(0, 280)
        );
    }

    public static Tweet fakeTweet() {
        return new Tweet(
                String.join(" ", faker.lorem().paragraphs(4)).substring(0, 280)
        );

    }
}
