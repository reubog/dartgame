package com.bognandi.dart.dartgame.gui.app.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GamePopups {

    private static final String FXML_GAME_MESSAGE = "/fxml/gameMessage.fxml";

    public Popup popupGameMessage(Window window, String message) {
        return nonInteractivePopup(window, new GameMessageController(message), FXML_GAME_MESSAGE);
    }

    private Popup nonInteractivePopup(Window window, Object controller, String resource) {
        try {
            Popup popup = new Popup();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            loader.setController(controller);
            popup.getContent().add(loader.load());
            popup.show(window);
            return popup;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class GameMessageController {

        @FXML
        private Label textLabel;

        private String text;

        public GameMessageController(String text) {
            this.text = text;
        }

        @FXML
        public void initialize() {
            textLabel.setText(text);
        }
    }
}
