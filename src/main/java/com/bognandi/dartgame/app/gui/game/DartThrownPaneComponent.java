package com.bognandi.dartgame.app.gui.game;

import javafx.scene.layout.VBox;

public class DartThrownPaneComponent extends VBox {

    private DartThrownComponent dart1 = new DartThrownComponent();
    private DartThrownComponent dart2 = new DartThrownComponent();
    private DartThrownComponent dart3 = new DartThrownComponent();

    public DartThrownPaneComponent() {
        getChildren().add(dart1);
        getChildren().add(dart2);
        getChildren().add(dart3);
    }



    public DartThrownComponent getDart1() {
        return dart1;
    }

    public DartThrownComponent getDart2() {
        return dart2;
    }

    public DartThrownComponent getDart3() {
        return dart3;
    }
}