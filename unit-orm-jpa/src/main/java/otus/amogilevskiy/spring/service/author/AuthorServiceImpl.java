package otus.amogilevskiy.spring.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.repository.author.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<Author> create(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return authorRepository.findById(id).map(author -> {
            author.removeBooks(Set.copyOf(author.getBooks()));
            return authorRepository.delete(author);
        }).orElse(false);
    }
}
