open module com.bognandi.dart.dartgame.gui.app {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires javafx.graphics;
    requires spring.beans;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires jakarta.annotation;
    requires freetts;
    requires spring.core;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires com.bognandi.dart.core;
    requires org.slf4j;
    //requires com.bognandi.dart.core.;
    requires com.bognandi.dart.dartboard.mqtt;
//    uses com.bognandi.dart.core;
//    uses com.bognandi.dart.core.dartgame;
//
//    opens com.bognandi.dart.app;
//    opens com.bognandi.dart.app.config;
//    opens com.bognandi.dart.app.service to spring.beans, spring.core;
//    opens com.bognandi.dart.app.service.audio to spring.beans;
//    opens com.bognandi.dart.app.service.speech to spring.beans;
//    opens com.bognandi.dart.app.gui to spring.beans, javafx.fxml, spring.core;

}