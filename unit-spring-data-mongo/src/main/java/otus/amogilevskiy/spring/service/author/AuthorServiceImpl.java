package otus.amogilevskiy.spring.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> create(Author author) {
        return Optional.of(authorRepository.save(author));
    }

    @Override
    public boolean deleteById(String id) {
        return authorRepository.findById(id).map(author -> {
            authorRepository.delete(author);
            return true;
        }).orElse(false);
    }
}
