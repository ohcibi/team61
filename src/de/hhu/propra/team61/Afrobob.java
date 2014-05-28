package de.hhu.propra.team61;

import de.hhu.propra.team61.GUI.CustomGrid;
import de.hhu.propra.team61.IO.GameState;;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * Created by dinii on 15.04.14.
 * ProPra Team 61:
 * Markus Brenneis 2194529 Git: mabre
 * Jan Ecknigk 2202505 Git: Jan-Ecknigk
 * Jessica Petrasch 2166230 Git: Jessypet
 * Kevin Gnyp 2166803 Git: Kegny
 * Simon Franz 2204765 Git: DiniiAntares
 * Project: Worms clone
 *
 */

public class Afrobob extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start (Stage mainwindow) throws NullPointerException {
        mainwindow.setTitle("Unicorns and Penguins! <3");
        mainwindow.setWidth(1000);
        mainwindow.setHeight(600);
        mainwindow.setResizable(false);
        CustomGrid grid = new CustomGrid();
        grid.setAlignment(Pos.CENTER);

        Button mstartl = new Button("Start local game");  //menue-start-local, menue-start-network, menue-options, menue-exit
        grid.setHalignment(mstartl, HPos.CENTER);  //centers the buttons, not needed for mstartn as the biggest button
        grid.add(mstartl, 0, 1);
        Button mstartsaved = new Button("Start saved game");
        grid.setHalignment(mstartsaved, HPos.CENTER);
        grid.add(mstartsaved, 0, 2);
        Button mstartn = new Button("Start network game");
        grid.setHalignment(mstartn, HPos.CENTER);
        grid.add(mstartn, 0, 3);
        Button moptions = new Button("Options");
        grid.setHalignment(moptions, HPos.CENTER);
        grid.add(moptions, 0, 4);
        Button mexit = new Button("Exit");
        grid.setHalignment(mexit, HPos.CENTER);
        grid.add(mexit, 0, 5);

        mstartl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                GameSettings gamesettings = new GameSettings();
                gamesettings.doSettings(mainwindow);                      //always pass 'mainwindow' to close it -> only one stage open at a time
            }
        });
        mstartsaved.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MapWindow mapwindow = new MapWindow(GameState.getSavedGameState(), mainwindow);
            }
        });
        mstartn.setOnAction(new EventHandler<ActionEvent>() {  //Click on button starts game.. well not yet
            @Override
            public void handle(ActionEvent e) {
                NetSettings netSettings = new NetSettings();
                netSettings.openPopUp(mainwindow);
            }
        });
        moptions.setOnAction(new EventHandler<ActionEvent>() {  //Click on button 'moptions' opens new window for options
            @Override
            public void handle(ActionEvent e) {
                OptionsWindow optionwindow = new OptionsWindow();
                optionwindow.doOptions(mainwindow);
            }
        });
        mexit.setOnAction(new EventHandler<ActionEvent>() {  //Click on Button 'mexit' closes window
            @Override
            public void handle(ActionEvent e) {
                mainwindow.close();
            }
        });

        Scene scene = new Scene(grid, 1000, 600);
        mainwindow.setScene(scene);
        scene.getStylesheets().add("file:resources/layout/css/menue.css");
        grid.getStyleClass().add("menuepane");
        mainwindow.show();
    }
}
