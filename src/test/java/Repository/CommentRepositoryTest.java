package Repository;

import Domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepositoryTest {

    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentRepository  = new CommentRepository();
    }

    @Test
    void getType() {
        assertEquals(Comment.class,commentRepository.getType());
    }
}