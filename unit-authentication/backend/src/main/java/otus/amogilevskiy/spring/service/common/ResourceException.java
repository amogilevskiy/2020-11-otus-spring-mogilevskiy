package otus.amogilevskiy.spring.service.common;

import lombok.Getter;

@Getter
public class ResourceException extends RuntimeException {

    protected final Resource resource;

    public ResourceException(Resource resource, String suffix) {
        super(String.format("%s_%s", resource.getValue(), suffix));
        this.resource = resource;
    }
}
