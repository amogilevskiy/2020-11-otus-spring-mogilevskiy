package otus.amogilevskiy.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import otus.amogilevskiy.spring.dto.comment.CommentDto;
import otus.amogilevskiy.spring.service.comment.CommentService;
import otus.amogilevskiy.spring.service.localization.LocalizationService;
import otus.amogilevskiy.spring.ui.comment.CommentView;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {

    private final CommentService commentService;
    private final CommentView commentView;
    private final LocalizationService localizationService;

    @ShellMethod(value = "Find all comments", key = {"fac", "find-all-comments"})
    public String findAllComments() {
        return commentService.findAll()
                .stream()
                .map(commentView::showCommentDetailView)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Find all comments by book id", key = {"fbc", "find-book-comments"})
    public String findBookComments(@ShellOption long bookId) {
        return commentService.findAllByBookId(bookId)
                .stream()
                .map(commentView::showCommentDetailView)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Add a new comment to a book", key = {"ac", "add-comment"})
    public String addComment(@ShellOption long bookId, @ShellOption String text) {
        return commentService.create(new CommentDto(null, bookId, text))
                .map(commentView::showCommentDetailView)
                .orElse(localizationService.localize("error.commentNotAdded"));
    }

    @ShellMethod(value = "Delete an existing comment by id", key = {"dc", "delete-comment"})
    public String deleteComment(@ShellOption long id) {
        if (commentService.deleteById(id)) {
            return localizationService.localize("success.commentDeleted");
        } else {
            return localizationService.localize("error.commentNotDeleted");
        }
    }

    @ShellMethod(value = "Update an existing comment by id", key = {"uc", "update-comment"})
    public String updateComment(@ShellOption long id, @ShellOption String text) {
        if (commentService.update(new CommentDto(id, null, text))) {
            return localizationService.localize("success.commentUpdated");
        } else {
            return localizationService.localize("error. commentNotUpdated");
        }
    }

}
