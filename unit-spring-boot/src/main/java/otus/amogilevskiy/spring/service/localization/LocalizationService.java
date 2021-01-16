package otus.amogilevskiy.spring.service.localization;

public interface LocalizationService {

    String localize(String key);
    
    String localize(String key, Object[] args);

}
