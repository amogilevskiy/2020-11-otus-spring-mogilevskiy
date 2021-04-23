package otus.amogilevskiy.spring.db;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import otus.amogilevskiy.spring.domain.Genre;
import otus.amogilevskiy.spring.repository.BookRepository;
import otus.amogilevskiy.spring.service.localization.LocalizationService;

@Component
@RequiredArgsConstructor
public class GenreEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;
    private final LocalizationService localizationService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);

        var document = event.getSource();
        var id = document.get("_id").toString();
        if (bookRepository.existsByGenreId(id)) {
            throw new RuntimeException(localizationService.localize("error.genreHasBooks"));
        }
    }

}