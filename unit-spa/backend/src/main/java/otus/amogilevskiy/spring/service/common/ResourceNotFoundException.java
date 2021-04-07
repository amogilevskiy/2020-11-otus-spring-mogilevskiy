package otus.amogilevskiy.spring.service.common;

public class ResourceNotFoundException extends ResourceException {

    public ResourceNotFoundException(Resource resource) {
        super(resource, "not_found");
    }

}
