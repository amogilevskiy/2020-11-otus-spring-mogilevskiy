package otus.amogilevskiy.spring.service.common;

public class ResourceAlreadyExistsException extends ResourceException {

    public ResourceAlreadyExistsException(Resource resource) {
        super(resource, "already_exists");
    }

}
