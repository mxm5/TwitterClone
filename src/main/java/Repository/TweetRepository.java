package Repository;

import Base.Repository.Repository;
import Domain.Tweet;
import Service.Services;

import javax.persistence.TypedQuery;
import java.util.List;

public class TweetRepository extends Repository<Tweet, Long> {
    @Override
    public Class<Tweet> getType() {
        return Tweet.class;
    }

    public List<Tweet> sortAllTweetsByDateDescending(int chunkSize, int pageCount) {
        TypedQuery<Tweet> query = getEntityManager().createQuery("select t from Tweet t order by t.publishDate desc", Tweet.class);
        return query.setFirstResult(chunkSize * pageCount).setMaxResults(chunkSize).getResultList();
    }

    public List<Tweet> sortUserTweetsByDateDescending(int chunkSize, int pageCount) {
        TypedQuery<Tweet> query = getEntityManager().createQuery("select t from Tweet t where t.owner.id = '" + Services.getLoggedUser().getId() + "' order by t.publishDate desc", Tweet.class);
        return query.setFirstResult(chunkSize * pageCount).setMaxResults(chunkSize).getResultList();
    }


}
