package otus.amogilevskiy.spring.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.dao.author.AuthorDao;
import otus.amogilevskiy.spring.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public Optional<Author> create(Author author) {
        return authorDao.create(author).flatMap(authorDao::findById);
    }

}
