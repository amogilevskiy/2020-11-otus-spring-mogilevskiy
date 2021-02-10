package otus.amogilevskiy.spring.facade.comment;

public interface CommentFacade {

    String findAllCommentsByBookId(long bookId);

    String addComment(long bookId, String text);

    String deleteCommentById(long id);

    String updateCommentById(long id, String text);
    
}
