package otus.amogilevskiy.spring.facade.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dto.comment.CreateCommentDto;
import otus.amogilevskiy.spring.dto.comment.UpdateCommentDto;
import otus.amogilevskiy.spring.service.comment.CommentService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.comment.CommentView;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentFacadeImpl implements CommentFacade {

    private final LocalizationService localizationService;
    private final CommentService commentService;
    private final CommentView commentView;

    @Override
    public String findAllCommentsByBookId(long bookId) {
        var comments = commentService.findAllByBookId(bookId);
        if (comments.size() > 0) {
            return comments.stream()
                    .map(commentView::showCommentDetailView)
                    .collect(Collectors.joining("\n"));
        } else {
            return localizationService.localize("error.bookHasNoComments");
        }

    }

    @Override
    public String addComment(long bookId, String text) {
        return commentService.create(new CreateCommentDto(bookId, text))
                .map(comment -> localizationService.localize("success.commentAdded", new Object[]{comment.getText()}))
                .orElse(localizationService.localize("error.commentNotAdded"));
    }

    @Override
    public String deleteCommentById(long id) {
        if (commentService.deleteById(id)) {
            return localizationService.localize("success.commentDeleted");
        }
        return localizationService.localize("error.commentNotFound");
    }

    @Override
    public String updateCommentById(long id, String text) {
        if (commentService.update(new UpdateCommentDto(id, text))) {
            return localizationService.localize("success.commentUpdated");
        }
        return localizationService.localize("error.commentNotUpdated");
    }
}
