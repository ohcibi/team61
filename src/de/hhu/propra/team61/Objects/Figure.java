package de.hhu.propra.team61.Objects;

import de.hhu.propra.team61.IO.JSON.JSONObject;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static de.hhu.propra.team61.JavaFxUtils.toHex;

/**
 * Created by kevgny on 14.05.14.
 */

public class Figure extends StackPane {
    private boolean facing_right = true; //Needed for Weapon class, MapWindow, etc.

    private String name;
    private int health;
    private int armor;

    private boolean isBurning;
    private boolean isPoisoned;
    private boolean isStuck;

    private boolean isActive;

    private Item selectedItem;

    private Rectangle2D hitRegion;
    private ImageView imageView;
    private Label hpLabel;
    private Color color;

    // In and Out
    public Figure(String name, int hp, int armor, boolean isBurning, boolean isPoisoned, boolean isStuck){
        this.name   = name;
        this.health = hp;
        this.armor  = armor;

        this.isBurning  = isBurning;
        this.isPoisoned = isPoisoned;
        this.isStuck    = isStuck;

        imageView = new ImageView();

        initialize();
    }

    public Figure(JSONObject input){
        imageView = new ImageView();
        imageView.setTranslateX(input.getDouble("position.x"));
        imageView.setTranslateY(input.getDouble("position.y"));

        this.name = input.getString("name");
        this.health = input.getInt("health");
        this.armor  = input.getInt("armor");
        this.isBurning  = input.getBoolean("isBurning");
        this.isPoisoned = input.getBoolean("isPoisoned");
        this.isStuck    = input.getBoolean("isStuck");

        System.out.println("FIGURE created from json");
        printAllAttributes(this);

        initialize();
    }

    public Figure(String name, JSONObject input){ //Create Figures by giving a name and applying Options TODO: Minor Adjustments after implementation of Options
        imageView = new ImageView();
        imageView.setTranslateX(input.getDouble("position.x"));
        imageView.setTranslateY(input.getDouble("position.y"));

        this.name = name;
        this.health = input.getInt("health");
        this.armor  = input.getInt("armor");
        this.isBurning  = input.getBoolean("isBurning");
        this.isPoisoned = input.getBoolean("isPoisoned");
        this.isStuck    = input.getBoolean("isStuck");

        System.out.println("FIGURE created from OptionsJson");
        printAllAttributes(this);

        initialize();
    }

    private void initialize() {
        setAlignment(Pos.TOP_LEFT);

        hitRegion = new Rectangle2D(imageView.getTranslateX(), imageView.getTranslateY(), 16, 16);

        selectedItem = null;
        hpLabel = new Label(health + "");
        hpLabel.setStyle("-fx-font-weight:bold;");
        hpLabel.setEffect(generateHpShadow());
        setPosition(getPosition()); // updates label position
        getChildren().add(hpLabel);

        Image image = new Image("file:resources/figures/pin.png", 16, 16, true, true);
        imageView.setImage(image);
        getChildren().add(imageView);
    }

    public JSONObject toJson(){
        JSONObject output = new JSONObject();
        output.put("name", name);
        output.put("health", health);
        output.put("armor", armor);
        output.put("position.x", imageView.getTranslateX()); // TODO save as array
        output.put("position.y", imageView.getTranslateY());

        output.put("isBurning", isBurning);
        output.put("isPoisoned", isPoisoned);
        output.put("isStuck", isStuck);
        return output;
    }

    public Effect generateHpShadow() {
        if(color == null) return  new DropShadow();

        DropShadow ds = new DropShadow();
        ds.setOffsetY(0);

        if(color.getBrightness() > .5) {
            ds.setColor(Color.GRAY);
        } else {
            ds.setColor(Color.LIGHTGRAY);
        }

        return ds;
    }

    public void setColor(Color color) {
        this.color = color;
        hpLabel.setTextFill(color);
        hpLabel.setEffect(generateHpShadow());
    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public int getArmor() {return armor;}
    public void setArmor(int armor) {this.armor = armor;}

    public boolean getIsBurning() {return isBurning;}
    public void setIsBurning(boolean isBurning){this.isBurning = isBurning;}

    public boolean getIsPoisoned() {return isPoisoned;}
    public void setIsPoisoned(boolean isPoisoned){this.isPoisoned = isPoisoned;}

    public boolean getIsStuck() {return isStuck;}
    public void setIsStuck(boolean isStuck){this.isStuck = isStuck;}

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        hpLabel.setEffect(generateHpShadow());
        if(isActive) {
            hpLabel.setStyle("-fx-font-weight:bold; -fx-border-color: rgba(255,0,0,.2); -fx-border-style: solid; -fx-border-width: 1px; -fx-border-radius: 5px;");
        } else {
            hpLabel.setStyle("-fx-font-weight:bold;");
        }
    }

    // TODO rethink parameter, /8 is bad!
    public void setPosition(Point2D position) {
        imageView.setTranslateX(8 * position.getX());
        imageView.setTranslateY(8 * position.getY());
        hitRegion = new Rectangle2D(imageView.getTranslateX(),imageView.getTranslateY(),hitRegion.getWidth(),hitRegion.getHeight());
        hpLabel.setTranslateX(imageView.getTranslateX());
        hpLabel.setTranslateY(imageView.getTranslateY()-15);
    }

    public Point2D getPosition() {
        return new Point2D(imageView.getTranslateX()/8, imageView.getTranslateY()/8);
    }

    public Item getSelectedItem(){
        return selectedItem;
    }
    public void setSelectedItem(Weapon select){
        if(selectedItem != null) {
            selectedItem.hide();
        }
        selectedItem = select;
        if(selectedItem != null) {
            select.setPosition(new Point2D(imageView.getTranslateX(), imageView.getTranslateY()));
            selectedItem.angleDraw(facing_right);
        }
    }

    public void setFacing_right(boolean facing_right) {
        this.facing_right = facing_right;
    }
    public boolean getFacing_right(){return facing_right;}

    public void sufferDamage(int damage) {
        health -= damage;
        if(health <= 0) {
            health = 0;
            Image image = new Image("file:resources/spawn.png", 8, 8, true, true); // TODO
            imageView.setImage(image);
            setPosition(new Point2D(-1000, -1000));
        }
        hpLabel.setText(health+"");
        System.out.println(name + " got damage " + damage + ", health at " + health);
    }

    public void setHealth(int hp) {
        this.health = hp;
        sufferDamage(0); // redraws the label and validated new hp
    }

    public int getHealth() {
        return health;
    }

    public Rectangle2D getHitRegion() {
        return hitRegion;
    }

    public Projectile shoot() throws NoMunitionException {
       // selectedItem.setPosition(new Point2D(imageView.getTranslateX(), imageView.getTranslateY())); // What is this for?
        return selectedItem.shoot();
    }

    //For testing purposes only
    private static void printAllAttributes(Figure testwurm){
        System.out.println("Health  : " + testwurm.getHealth());
        System.out.println("Armor   : " + testwurm.getArmor());
        System.out.println("Name    : " + testwurm.getName());
        System.out.println("Burning : " + testwurm.getIsBurning());
        System.out.println("Poisoned: " + testwurm.getIsPoisoned());
        System.out.println("Stuck   : " + testwurm.getIsStuck());

        System.out.println("Position: " + testwurm.getPosition());
        System.out.println();
    }
}
