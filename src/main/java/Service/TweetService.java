package Service;

import Base.Service.Service;
import Domain.Tweet;
import Repository.TweetRepository;
import java.util.List;
public class TweetService extends Service<Tweet, Long, TweetRepository> {

    public TweetService(TweetRepository repository) {
        super(repository);
    }

    public List<Tweet> loadTwentyRecentTweets(int pageCount) {
        return repository.sortAllTweetsByDateDescending(20, pageCount);
    }


    public List<Tweet> loadUsersTwentyRecentTweets(int pageCount) {
        return repository.sortUserTweetsByDateDescending(20, pageCount);
    }

}
