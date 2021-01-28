package otus.amogilevskiy.spring.service.form;

public interface FormService {

    void showLabelField(String field);

    String showStringFormField(String field);

    boolean showBooleanFormField(String field);

    long showIntegerFormField(String field);

}
