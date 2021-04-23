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

    @ShellMethod(value = "Find all comments ", key = {"fac", "find-all-comments"})
    public String findAllComments() {
        return commentService.findAll()
                .stream()
                .map(commentView::showCommentDetailView)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Find all comments by book id", key = {"fbc", "find-book-comments"})
    public String findBookComments(@ShellOption String bookId) {
        return commentService.findAllByBookId(bookId)
                .stream()
                .map(commentView::showCommentDetailView)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Add a new comment to a book", key = {"ac", "add-comment"})
    public String addComment(@ShellOption String bookId, @ShellOption String text) {
        if (commentService.create(new CommentDto(bookId, text))) {
            return localizationService.localize("success.commentAdded");
        } else {
            return localizationService.localize("error.commentNotAdded");
        }
    }

}
