module com.bognandi.dartgame.app {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires javafx.graphics;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires org.slf4j;
    requires jakarta.annotation;
    requires freetts;
    requires spring.core;
    requires static lombok;
    requires blessed;
    requires org.jetbrains.annotations;
    requires java.desktop;

    opens com.bognandi.dartgame.app;
    opens com.bognandi.dartgame.app.config;
    opens com.bognandi.dartgame.app.service to spring.beans, spring.core;
    opens com.bognandi.dartgame.app.service.audio to spring.beans;
    opens com.bognandi.dartgame.app.service.speech to spring.beans;
    opens com.bognandi.dartgame.app.gui to spring.beans, javafx.fxml, spring.core;

    opens com.bognandi.dartgame.domain.dartgame;
}