package Repository;

import Domain.Tweet;
import Domain.User;
import Service.Services;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class TweetRepositoryTest {


    TweetRepository tweetRepository;

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    void setUp() {
        tweetRepository = new TweetRepository();
    }

    @AfterEach
    void tearDown() {

    }

    //all repositories common
    @Test
    void close() {
        //after calling close it should throw error when calling repository methods
        Tweet hello = new Tweet(
                "hello this is new tweet"
        );
        tweetRepository.close();
//Tweet mock = mock(Tweet.class);
//System.out.println(mock.getOwner().getFirstName()+mock.getTweetText()+mock.getId()+"=======================================================");
//mock uses nulllllls
//save
        assertThrows(IllegalStateException.class, () -> tweetRepository.save(hello));
//delete
        assertThrows(IllegalStateException.class, () -> tweetRepository.delete(hello));
//getById
        assertThrows(IllegalStateException.class, () -> tweetRepository.getById(1L));
//getAll
        assertThrows(IllegalStateException.class, () -> tweetRepository.getAll());
//getChunkOfAll
        assertThrows(IllegalStateException.class, () -> tweetRepository.getChunkOfAll(2, 0));
//getType
//assertThrows(IllegalStateException.class, () -> tweetRepository.getType());
//sortTweetsByDateDescending
        assertThrows(IllegalStateException.class, () -> tweetRepository.sortAllTweetsByDateDescending(2, 0));
//sortUserTweetsByDateDescending
//mock Services.user.getlogedinuservyid
//because this one needs the services and to find the current user
//Services services = mock(Services.class);
//User mockLoggedInUser = mock(User.class);
//(Services.getLoggedUser());
        try (MockedStatic<Services> servicesMockedStatic = mockStatic(Services.class)) {
            servicesMockedStatic.when(Services::getLoggedUser).thenReturn(
                    mock(User.class)
            );
            assertThrows(IllegalStateException.class, () -> tweetRepository.sortUserTweetsByDateDescending(2, 0));
        }
//findUserNamePasswordInDb
//assertThrows(IllegalStateException.class, () -> tweetRepository.);
//getTransaction
        assertThrows(IllegalStateException.class, () -> tweetRepository.getTransaction());

//getEntityManager
        assertThrows(IllegalStateException.class, () -> tweetRepository.getEntityManager());
    }


    @Test
    void getTransaction() {
        //it should return a transaction when called
    }

    @Test
    void getEntityManager() {
        //it should return an entity manager when called
    }

    @Test
    void save() {
        //it should save and update objects to database when called
    }

    @Test
    void delete() {
        //it should delete objects from database when called
    }

    @Test
    void getById() {
        //it should find elements saved in the database when given their ids
    }

    @Test
    void getAll() {
        //it should return all objects saved in database

    }

    @Test
    void getChunkOfAll() {
        //it should return a part of data determined in the database
    }


    @Test
    void getType() {
        //it should return the type of object when called
    }

    // for tweet
    @Test
    void sortTweetsByDateDescending() {
        //it should sort tweets by date descending;
    }

    @Test
    void sortUserTweetsByDateDescending() {
        //it should sort user tweets by date descending
    }

// for user

//    @Test
//    void findUserNamePasswordInDb() {
//    }
//
//    @Test
//    void findUserByUserName() {
//    }
}