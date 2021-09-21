package Domain;

import Base.Domain.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Tweet extends BaseEntity<Long> {


    @Column(name = "tweet_text", length = 280)
    private String tweetText;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    private User owner;


    @Column(name = "publish_date")
    private Date publishDate;

    public Tweet(String tweetText) {
        this.tweetText = tweetText;
        this.publishDate = Date.valueOf(LocalDate.now());
    }



    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Tweet_liked",
            joinColumns = @JoinColumn(name = "tweet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> likedUsers = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Tweet_dislike",
            joinColumns = @JoinColumn(name = "tweet_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> dislikedUsers = new HashSet<>();

    @OrderBy("publishDate desc")
    @OneToMany(mappedBy = "inReplyToTweet", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addLikeFromUser(User user) {

        boolean remove = dislikedUsers.remove(user);
        likedUsers.add(user);

    }

    public void addDislikeFromUser(User user) {
        boolean remove = likedUsers.remove(user);
        dislikedUsers.add(user);
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetText='" + tweetText + '\'' +
                ", owner=" + owner +
                ", publishDate=" + publishDate +
                '}';
    }

    public boolean removeLikeFromUser(User user) {

        return likedUsers.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(getId(), tweet.getId()) && Objects.equals(tweetText, tweet.tweetText) && Objects.equals(publishDate, tweet.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), tweetText, owner, publishDate);
    }

    @Override
    public void setDateToNow() {
        this.publishDate = getNow();
    }

    @Override
    public void cleanAssociations() {
        this.setLikedUsers(new HashSet<>());
        this.setDislikedUsers(new HashSet<>());

    }

}
