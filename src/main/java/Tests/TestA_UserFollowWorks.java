package Tests;

import Domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class TestA_UserFollowWorks {
    public static void main(String[] args) {
        EntityManagerFactory twitter_clone =
                Persistence.
                        createEntityManagerFactory("twitter_clone");
        EntityManager entityManager = twitter_clone.createEntityManager();


        createUserFolloweeAndFollower(entityManager);
        readFollowers(entityManager);

        entityManager.close();
        twitter_clone.close();


    }

    private static void readFollowers(EntityManager entityManager) {
        User akl = entityManager.find(User.class, 1L);
        User mns = entityManager.find(User.class, 2L);
        User mxm = entityManager.find(User.class, 3L);

        System.out.println("akl============");
        Set<User> followers2 = akl.getFollowers();
        System.out.println(followers2.size());
        followers2.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName()));
        followers2 = akl.getFollowing();
        System.out.println(followers2.size());
        followers2.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName()));

        System.out.println("mxn================");
        Set<User> following1 = mxm.getFollowers();
        System.out.println(following1.size());
        following1.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName()));
        following1 = mxm.getFollowing();
        System.out.println(following1.size());
        following1.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName()));
        System.out.println("mns===============");

        Set<User> following = mns.getFollowers();
        System.out.println(following.size());
        following.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName()));

        following = mns.getFollowing();
        System.out.println(following.size());
        following.forEach(user -> System.out.println(user.getFirstName() + " " + user.getLastName()));
    }

    private static void createUserFolloweeAndFollower(EntityManager entityManager) {
        User mxm = new User("mohammad", "moahammmadian", "mxm", "123");
        User akl = new User("ali", "kamandlu", "akl", "123");
        User mns = new User("mohammad", "nasr", "mns", "123");
        entityManager.getTransaction().begin();
        entityManager.persist(akl);
        entityManager.persist(mns);
        mxm.followedFromUser(akl);
        mxm.followedFromUser(mns);
        entityManager.persist(mxm);
        entityManager.getTransaction().commit();
    }
}
