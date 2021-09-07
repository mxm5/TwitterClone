package Service;

import Base.Service.Service;
import Domain.Comment;
import Repository.CommentRepository;

public class CommentService extends Service<Comment, Long, CommentRepository> {

    public CommentService(CommentRepository repository) {
        super(repository);
    }
}
