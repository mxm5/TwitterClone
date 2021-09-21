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
import Domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        Collection<User> all = userRepository.getAll();
        all.forEach(
                user -> userRepository.delete(user)
        );


    }

    @Test
    @DisplayName("it should be the same type with user type")
    void getType() {
        assertEquals(User.class, userRepository.getType());
    }

    @Test
    @DisplayName("it should return a user if we provide the user.userName and user.password of that user")
    void findUserNamePasswordInDb() throws Exception {
        User ahx9 = new User("ahx9", "123");
        User userNamePasswordInDb = userRepository.findUserNamePasswordInDb(ahx9);
        assertNull(userNamePasswordInDb);

        User ahx = new User("ali", "hasani", "ahx9", "123");
        userRepository.save(ahx);
        userNamePasswordInDb = userRepository.findUserNamePasswordInDb(ahx9);
        assertEquals(ahx, userNamePasswordInDb);
        userRepository.delete(ahx);
    }

    @Test
    @DisplayName("it should return a user if we provide a users userName")
    void searchUserByUserNames() throws Exception {
        List<User> users = userRepository.searchUserByUserNames("\n۱۲۳123%^#!@&*-++/?><ؤ".repeat(12));
        //findsAll which is 0
        assertEquals(0, users.size());

        users = userRepository.searchUserByUserNames("a");
        users.forEach(
                System.out::println
        );
        assertEquals(0, users.size());
        User user = new User(
                "ali",
                "hasani",
                "aliHsn",
                "123"
        );
        userRepository.save(
                user
        );
        users = userRepository.searchUserByUserNames("");
        assertEquals(1, users.size());
        users = userRepository.searchUserByUserNames("a");
        assertEquals(1, users.size());
        users = userRepository.searchUserByUserNames("l");
        assertEquals(1, users.size());
        userRepository.delete(user);

    }
}