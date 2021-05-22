package otus.amogilevskiy.spring.service.common;

import lombok.Getter;

@Getter
public enum Resource {

    BOOK("book"),
    AUTHOR("author"),
    GENRE("genre"),
    COMMENT("comment");

    private final String value;

    Resource(String value) {
        this.value = value;
    }
    
}
