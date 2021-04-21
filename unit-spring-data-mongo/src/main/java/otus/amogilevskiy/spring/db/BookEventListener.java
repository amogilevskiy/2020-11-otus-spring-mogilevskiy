package otus.amogilevskiy.spring.db;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import otus.amogilevskiy.spring.domain.Book;
import otus.amogilevskiy.spring.repository.CommentRepository;

@Component
@RequiredArgsConstructor
public class BookEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);

        var document = event.getSource();
        var id = document.get("_id").toString();

        var comments = commentRepository.findAllByBookId(id);
        if (comments.size() > 0) {
            commentRepository.deleteAll(comments);
        }
    }

}
