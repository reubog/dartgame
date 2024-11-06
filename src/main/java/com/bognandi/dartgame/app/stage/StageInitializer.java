package com.bognandi.dartgame.app.stage;

import com.bognandi.dartgame.app.event.StageReadyEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:fxml/main.fxml")
    private Resource quizResource;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    private String applicationTitle;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        ConfigurableApplicationContext springContext = event.getAppContext();
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(quizResource.getURL());
//            fxmlLoader.setControllerFactory(springContext::getBean);
//            Parent parent = fxmlLoader.load();
//            stage.setScene(new Scene(parent, 800, 600));
            stage.setTitle(applicationTitle);
            stage.show();
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
    }
}
