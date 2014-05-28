package de.hhu.propra.team61;

import de.hhu.propra.team61.GUI.BigStage;
import de.hhu.propra.team61.GUI.CustomGrid;
import de.hhu.propra.team61.IO.JSON.JSONObject;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static de.hhu.propra.team61.JavaFxUtils.toHex;

/**
 * Created by Jessypet on 27.05.14.
 */
public class NetSettings extends Application {

    TextField ipField = new TextField();
    Stage stageToClose;
    TextField weapon1 = new TextField("50");
    TextField weapon2 = new TextField("50");
    TextField weapon3 = new TextField("5");
    TextField sizefield = new TextField("4");
    TextField numberOfTeams = new TextField("2");
    TextField nameField = new TextField();
    ColorPicker colorpicker = new ColorPicker();

    public void openPopUp(Stage stageToClose) {
        this.stageToClose = stageToClose;
        Stage netpopup = new Stage();
        netpopup.setTitle("Start network game");
        netpopup.setWidth(400);
        netpopup.setHeight(200);
        netpopup.setResizable(false);
        CustomGrid popGrid = new CustomGrid();
        popGrid.setAlignment(Pos.CENTER_LEFT);
        popGrid.getColumnConstraints().add(new ColumnConstraints(110));
        popGrid.getColumnConstraints().add(new ColumnConstraints(210));

        Text ipError = new Text();
        popGrid.add(ipError, 0, 6);
        Button hostGame = new Button("Host a game");
        popGrid.add(hostGame, 0, 0);
        hostGame.setOnAction(new EventHandler<ActionEvent>() {  //Click on Button 'mexit' closes window
            @Override
            public void handle(ActionEvent e) {
                netpopup.close();
                hostGame();
            }
        });
        Button joinGame = new Button("Join a game");
        popGrid.add(joinGame, 0, 1);
        joinGame.setOnAction(new EventHandler<ActionEvent>() {  //Click on Button 'mexit' closes window
            @Override
            public void handle(ActionEvent e) {
                if (ipField.getText().length() > 0) { netpopup.close();
                    joinGame(ipField.getText()); }
                else { ipError.setText("Error: No IP-Address entered."); }

            }
        });
        ipField.setPromptText("Enter the IP-Address.");
        popGrid.add(ipField, 1, 1);
        Text note = new Text("Note: If you're on the same computer");
        Text note2 = new Text("as the host, type in 'localhost'.");
        popGrid.add(note, 1, 3);
        popGrid.add(note2, 1, 4);
        Scene popScene = new Scene(popGrid);
        netpopup.setScene(popScene);
        netpopup.show();
    }

    public void hostGame() {
        BigStage hostStage = new BigStage("Settings for network game");
        CustomGrid hostGrid = new CustomGrid();
        hostGrid.setAlignment(Pos.TOP_LEFT);
        Label generalSettings = new Label("Choose general settings:");
        hostGrid.add(generalSettings, 0, 0, 2, 1);
        Text teamSize = new Text("Size of teams: ");
        hostGrid.add(teamSize, 0, 1);
        hostGrid.add(sizefield, 1, 1);
        Text teamNumber = new Text("Max. number of teams: ");
        hostGrid.add(teamNumber, 2, 1);
        hostGrid.add(numberOfTeams, 3, 1);
        Text enter = new Text ("Enter the quantity of projectiles for each weapon:");
        hostGrid.add(enter, 0, 2, 3, 1);
        Text w1 = new Text("Weapon 1: ");
        hostGrid.add(w1, 0, 3);
        hostGrid.add(weapon1, 1, 3);
        Text w2 = new Text("Weapon 2: ");
        hostGrid.add(w2, 2, 3);
        hostGrid.add(weapon2, 3, 3);
        Text w3 = new Text("Weapon 3: ");
        hostGrid.add(w3, 4, 3);
        hostGrid.add(weapon3, 5, 3);

        Label teamSettings = new Label("Choose settings for your team:");
        hostGrid.add(teamSettings, 0, 5, 2, 1);
        Text name = new Text("Team-Name:");
        hostGrid.add(name, 0, 6);
        hostGrid.add(nameField, 1, 6);
        Text color = new Text("Color:");
        hostGrid.add(color, 0, 7);
        hostGrid.add(colorpicker, 1, 7);

        Button cont = new Button("Host game!");
        hostGrid.add(cont, 0, 10);
        cont.setOnAction(new EventHandler<ActionEvent>() {  //Click on Button 'mexit' closes window
            @Override
            public void handle(ActionEvent e) {
                NetLobby netlobby = new NetLobby(toJson(), hostStage);
            }
        });
        Button back = new Button("Back");
        hostGrid.add(back, 1, 10);
        back.setOnAction(new EventHandler<ActionEvent>() {  //Click on Button 'mexit' closes window
            @Override
            public void handle(ActionEvent e) {
                hostStage.close();
                stageToClose.show();
            }
        });

        Scene hostScene = new Scene(hostGrid, 1000, 600);
        hostStage.setScene(hostScene);
        hostScene.getStylesheets().add("file:resources/layout/css/settings.css");
        hostGrid.getStyleClass().add("settingpane");
        hostStage.show();
        stageToClose.close();
    }

    public void joinGame(String ipAddress) {

    }

    public JSONObject toJson() {
        JSONObject output = new JSONObject();
        output.put("numberOfTeams", numberOfTeams);   //save number of teams
        output.put("team-size", sizefield.getText()); //save size of teams
        output.put("weapon1", weapon1.getText()); // TODO make array instead of using suffix
        output.put("weapon2", weapon2.getText());
        output.put("weapon3", weapon3.getText());
        JSONObject team = getJsonForTeam(nameField.getText(), colorpicker);
        output.put("teamhost", team);
        return output;
    }

    public JSONObject getJsonForTeam(String name, ColorPicker colorpicker) {
        JSONObject team = new JSONObject(); //create JSONArray with 2 objects name and color, one JSONArray for each team
        team.put("name", name);
        team.put("color", toHex(colorpicker.getValue()));
        return team;
    }

    @Override
    public void start(Stage filler) {}
}
