package otus.amogilevskiy.spring.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.amogilevskiy.spring.domain.Author;
import otus.amogilevskiy.spring.dto.author.AuthorDto;
import otus.amogilevskiy.spring.repository.AuthorRepository;
import otus.amogilevskiy.spring.service.common.Resource;
import otus.amogilevskiy.spring.service.common.ResourceNotFoundException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author findById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Resource.AUTHOR));
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public List<Author> findAllById(Iterable<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    @Transactional
    @Override
    public Author create(AuthorDto dto) {
        var author = Author.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .build();
        return authorRepository.save(author);
    }

    @Override
    public Author updateById(long id, AuthorDto dto) {
        var author = findById(id);
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setMiddleName(dto.getMiddleName());
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        var author = findById(id);
        author.removeBooks(Set.copyOf(author.getBooks()));
        authorRepository.deleteById(id);
        return true;
    }
}
