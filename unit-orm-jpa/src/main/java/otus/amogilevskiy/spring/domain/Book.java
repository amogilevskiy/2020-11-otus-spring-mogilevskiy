package otus.amogilevskiy.spring.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NamedEntityGraph(name = "books-with-authors-genre-graph",
        attributeNodes = {
                @NamedAttributeNode("authors"), @NamedAttributeNode("genre")
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"authors", "genre"})
@EqualsAndHashCode(exclude = {"authors", "genre"})
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "book_authors", joinColumns = {@JoinColumn(name = "book_id")}, inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private Set<Author> authors;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Genre genre;

    public Book(Long id) {
        this.id = id;
    }

}
