package Domain;

import Base.Domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<Long> {

    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "join_date")
    private Date joinDate;


    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Tweet> publishedTweets = new HashSet<>();

    public void publishTweet(Tweet tweet) {
        this.publishedTweets.add(tweet);
    }

    @ManyToMany(mappedBy = "followers", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<User> following = new HashSet<>();

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @ManyToMany(cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "User_follow",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"))
    private Set<User> followers = new HashSet<>();


    public void followedFromUser(User user) {
        followers.add(user);
    }

    public void unFollowedFromUser(User user) {

        followers.remove(user);
    }

    @ManyToMany(mappedBy = "likedUsers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Tweet> likedTweets = new HashSet<>();

    @ManyToMany(mappedBy = "dislikedUsers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Tweet> tweetsDisliked = new ArrayList<>();

    @ManyToMany(mappedBy = "likedUsers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> likedComments = new ArrayList<>();

    @ManyToMany(mappedBy = "dislikedUsers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> dislikedComments = new ArrayList<>();


    @OneToMany(mappedBy = "ownerUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Comment> publishedComments;

    public User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.joinDate = Date.valueOf(LocalDate.now());
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(joinDate, user.joinDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), firstName, lastName, userName, password, joinDate);
    }

    @Override
    public void setDateToNow() {
        this.joinDate = getNow();
    }

    @Override
    public void cleanAssociations() {
        this.setDislikedComments(new ArrayList<>());
        this.setFollowers(new HashSet<>());
        this.setLikedComments(new ArrayList<>());
        this.setLikedTweets(new HashSet<>());
    }
}
