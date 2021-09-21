package Repository;

import Base.Repository.Repository;
import Domain.Comment;

public class CommentRepository extends Repository<Comment,Long> {
    @Override
    public Class<Comment> getType() {
        return Comment.class;
    }

}
