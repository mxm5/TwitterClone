package Domain;

import Base.Domain.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Comment extends BaseEntity<Long> {


    @Column(name = "comment_text", length = 280)
    private String commentText;

    @Column(name = "publish_date")
    private Date publishDate;

    @Override
    public void setDateToNow() {
        this.publishDate = getNow();
    }

    @Override
    public void cleanAssociations() {
        this.setDislikedUsers(new ArrayList<>());
        this.setDislikedUsers(new ArrayList<>());
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Comment_like",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private List<User> likedUsers = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Comment_dislike",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private List<User> dislikedUsers = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "in_reply_to_tweet_id")
    private Tweet inReplyToTweet;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "in_reply_to_comment_id")
    private Comment inReplyToComment;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User ownerUser;

    public Comment(String commentText) {
        this.commentText = commentText;
        this.publishDate = Date.valueOf(LocalDate.now());
    }


    public Comment(String commentText, Date publishDate, Comment inReplyToComment) {
        this.commentText = commentText;
        this.publishDate = Date.valueOf(LocalDate.now());
        this.inReplyToComment = inReplyToComment;
    }

    public Comment(String commentText, Date publishDate, Tweet inReplyToTweet) {
        this.commentText = commentText;
        this.publishDate = publishDate;
        this.inReplyToTweet = inReplyToTweet;
    }

    public void addLikeFromUser(User user) {
        dislikedUsers.remove(user);
        likedUsers.add(user);
    }

    public void addDislikeFromUser(User user) {
        likedUsers.remove(user);
        dislikedUsers.add(user);
    }

    public Comment(String commentText, Tweet inReplyToTweet, Comment inReplyToComment, User ownerUser) {
        this.commentText = commentText;
        this.inReplyToTweet = inReplyToTweet;
        this.inReplyToComment = inReplyToComment;
        this.ownerUser = ownerUser;
    }

    public Comment(String commentText, Tweet inReplyToTweet, User ownerUser) {
        this.commentText = commentText;
        this.inReplyToTweet = inReplyToTweet;
        this.ownerUser = ownerUser;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentText='" + commentText + '\'' +
                ", publishDate=" + publishDate +
                ", inReplyToTweet=" + inReplyToTweet +
                ", inReplyToComment=" + inReplyToComment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId()) && Objects.equals(commentText, comment.commentText) && Objects.equals(publishDate, comment.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), commentText, publishDate);
    }
}
