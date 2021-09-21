package Repository;

import Domain.Tweet;
import Domain.User;
import Service.Services;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaDescriptor;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class TweetRepositoryTest {


    TweetRepository tweetRepository;


    @BeforeEach
    void setUp() {
        tweetRepository = new TweetRepository();
        Collection<Tweet> all = tweetRepository.getAll();
        all.forEach(
                tweet -> tweetRepository.delete(tweet)
        );
    }

    @AfterEach
    void tearDown() {
        tweetRepository.close();
    }

    @Test
    @DisplayName("after calling the close method all tweet repository methods must return illegal exception state when called")
    void close() throws Exception {
        Tweet tweet = saveTweetInDb();
        //after calling close it should throw error when calling repository methods
        tweetRepository.close();
        //save
        assertThrows(IllegalStateException.class, () -> tweetRepository.save(tweet));
        //delete
        assertThrows(IllegalStateException.class, () -> tweetRepository.delete(tweet));
        //getById
        assertThrows(IllegalStateException.class, () -> tweetRepository.getById(1L));
        //getAll
        assertThrows(IllegalStateException.class, () -> tweetRepository.getAll());
        //getChunkOfAll
        assertThrows(IllegalStateException.class, () -> tweetRepository.getChunkOfAll(2, 0));
        //getType
        assertThrows(IllegalStateException.class, () -> tweetRepository.sortAllTweetsByDateDescending(2, 0));
        //because mocking static methods returns auto closable we should use try with resource
        try (MockedStatic<Services> servicesMockedStatic = mockStatic(Services.class)) {
            //mocking a static method needs inline-mockito package to be added
            servicesMockedStatic.when(Services::getLoggedUser).thenReturn(
                    mock(User.class)
            );
            //sortUserTweetsByDateDescending
            assertThrows(IllegalStateException.class, () -> tweetRepository.sortUserTweetsByDateDescending(2, 0));
        }
        //findUserNamePasswordInDb
        assertThrows(IllegalStateException.class, () -> tweetRepository.getTransaction());

        //getEntityManager
        assertThrows(IllegalStateException.class, () -> tweetRepository.getEntityManager());
    }


    @Test
    @DisplayName("it should return a transaction when called")
    void getTransaction() {
        assertInstanceOf(EntityTransaction.class, tweetRepository.getTransaction());
    }

    @Test
    @DisplayName("it should return an entity manager when called")
    void getEntityManager() {
        assertInstanceOf(EntityManager.class, tweetRepository.getEntityManager());
    }

    @Test
    @DisplayName("it should save and update objects to database when called")
    void save() throws Exception {
        Tweet expectedTwt = saveTweetInDb();
        Long expectedId = expectedTwt.getId();
        EntityManager entityManager = tweetRepository.getEntityManager();
        Tweet originalTwt = entityManager.find(Tweet.class, expectedId);
        assertEquals(originalTwt, expectedTwt);
        /// update
        originalTwt.setTweetText("bye");
        tweetRepository.save(originalTwt);
        Tweet originalTwtUpdated = entityManager.find(Tweet.class, originalTwt.getId());
        assertEquals("bye", originalTwtUpdated.getTweetText());
        ///delete
        tweetRepository.delete(originalTwtUpdated);

    }

    @Test
    @DisplayName("it should delete objects from database when called")
    void delete() throws Exception {
        // when we save a tweet object in database
        // and use delete to remove it we should not find it
        // if we search the database
        Tweet tweet = saveTweetInDb();
        Long tweetId = tweet.getId();
        tweetRepository.delete(tweet);
        Tweet result = tweetRepository.getEntityManager().find(Tweet.class, tweetId);
        assertNull(result);

    }

    @Test
    @DisplayName("it should find elements saved in the database when given their ids")
    void getById() throws Exception {
        Tweet tweet = saveTweetInDb();
        Long tweetId = tweet.getId();
        Tweet result = tweetRepository.getEntityManager().find(Tweet.class, tweetId);
        assertEquals(tweet, result);
        tweetRepository.delete(result);
    }

    private Tweet saveTweetInDb() throws Exception {
        Tweet tweet = new Tweet("hello");
        tweetRepository.save(tweet);
        return tweet;
    }

    @Test
    @DisplayName("it should return all objects saved in database")
    void getAll() throws Exception {
        //when we have no item we should get 0 size of returned list
        Collection<Tweet> all = tweetRepository.getAll();
        assertEquals(0, all.size());
        Tweet tweet = saveTweetInDb();
        Collection<Tweet> all2 = tweetRepository.getAll();
        assertEquals(1, all2.size());
        tweetRepository.delete(tweet);

    }

    @Test
    @DisplayName("it should return a part of data determined in the database")
    void getChunkOfAll() throws Exception {
        //when have no item in database it should return zero items
        Collection<Tweet> chunkOfAll = tweetRepository.getChunkOfAll(20, 0);
        assertEquals(0, chunkOfAll.size());
        chunkOfAll = tweetRepository.getChunkOfAll(10, 3);
        assertEquals(0, chunkOfAll.size());
        chunkOfAll = tweetRepository.getChunkOfAll(0, 3);
        assertEquals(0, chunkOfAll.size());
        //when we have one record it should return one
        Tweet tweet = saveTweetInDb();
        chunkOfAll = tweetRepository.getChunkOfAll(10, 3);
        assertEquals(0, chunkOfAll.size());
        chunkOfAll = tweetRepository.getChunkOfAll(10, 0);
        assertEquals(1, chunkOfAll.size());
        chunkOfAll = tweetRepository.getChunkOfAll(2, 0);
        assertEquals(1, chunkOfAll.size());
        tweetRepository.delete(tweet);

    }


    @Test
    @DisplayName("it should return the type of object when called")
    void getType() {
        assertEquals(Tweet.class, tweetRepository.getType());
    }


    @Test
    @DisplayName("it should sort tweets by date descending")
    void sortTweetsByDateDescending() throws Exception {
        Tweet tweet1 = new Tweet("twt 1999");
        tweet1.setPublishDate(Date.valueOf(LocalDate.of(1999, 9, 9)));// old last
        tweetRepository.save(tweet1);
        Tweet tweet2 = new Tweet("twt 2000");
        tweet2.setPublishDate(Date.valueOf(LocalDate.of(2000, 9, 9)));//  new first
        tweetRepository.save(tweet2);
        List<Tweet> tweets = tweetRepository.sortAllTweetsByDateDescending(2, 0);

        System.out.println("==========================================");
        System.out.println(tweets);
        System.out.println("==========================================");

        // old tweet should be in second place
        assertEquals(tweet1, tweets.get(1));
        // new tweet should not be in second place
        assertNotEquals(tweet2, tweets.get(1));
        // new tweet should be in first place
        assertEquals(tweet2, tweets.get(0));
        // old tweet should not be in first place
        assertNotEquals(tweet1, tweets.get(0));

        tweetRepository.delete(tweet1);
        tweetRepository.delete(tweet2);
    }

    @Test
    @DisplayName("it should sort user tweets by date descending")
    void sortUserTweetsByDateDescending() throws Exception {

        try (MockedStatic<Services> servicesMockedStatic = mockStatic(Services.class)) {

            User user = new User();
            user.setFirstName("x");

            UserRepository userRepository = new UserRepository();

            Tweet tweet = new Tweet("twt 1999");
            tweet.setPublishDate(Date.valueOf(LocalDate.of(1999, 9, 9)));// old last
            tweet.setOwner(user);

            tweetRepository.save(tweet);

            Tweet tweet2 = new Tweet("twt 2000");
            tweet2.setPublishDate(Date.valueOf(LocalDate.of(2000, 9, 9)));//  new first
            tweet2.setOwner(user);

            tweetRepository.save(tweet2);

            servicesMockedStatic.when(Services::getLoggedUser).thenReturn(
                    user
            );
            List<Tweet> tweets = tweetRepository.sortUserTweetsByDateDescending(2, 0);

            // old tweet should be in second place
            assertEquals(tweet, tweets.get(1));
            // new tweet should not be in second place
            assertNotEquals(tweet2, tweets.get(1));
            // new tweet should be in first place
            assertEquals(tweet2, tweets.get(0));
            // old tweet should not be in first place
            assertNotEquals(tweet, tweets.get(0));
            tweetRepository.getTransaction().begin();
            tweetRepository.getEntityManager().remove(user);
            tweetRepository.getTransaction().commit();
            tweetRepository.delete(tweet);
            tweetRepository.delete(tweet2);

        }
    }
}