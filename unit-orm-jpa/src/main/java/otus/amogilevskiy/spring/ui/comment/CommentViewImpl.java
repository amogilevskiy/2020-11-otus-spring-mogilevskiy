package otus.amogilevskiy.spring.ui.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Comment;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Service
@RequiredArgsConstructor
public class CommentViewImpl implements CommentView {

    private final LocalizationService localizationService;

    @Override
    public String showCommentDetailView(Comment comment) {
        return localizationService.localize("comment.detail", new Object[]{comment.getId(), comment.getText()});
    }
}
