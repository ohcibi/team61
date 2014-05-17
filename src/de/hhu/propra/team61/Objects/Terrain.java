package de.hhu.propra.team61.Objects;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * A GridPane representing a terrain.
 * The class has methods for getting spawn points, walkability checks and destroying terrain (TODO actually not)
 * Created by markus on 17.05.14.
 */
public class Terrain extends GridPane {
    private ArrayList<ArrayList<Character>> terrain;
    private ArrayList<Point2D> spawnPoints;

    /**
     * @param terrain 2-D-ArrayList containing the terrain to be displayed
     */
    public Terrain(ArrayList<ArrayList<Character>> terrain) {
        this.terrain = terrain;

        spawnPoints = new ArrayList<Point2D>();

        //Draw Terrain
        setGridLinesVisible(true); //Gridlines on/off
        setAlignment(Pos.CENTER);

        for(int i=0; i < terrain.size(); i++){
            for(int j=0; j < terrain.get(i).size(); j++){
                char terraintype = terrain.get(i).get(j);
                String loadImg ="file:resources/"; // TODO this was removed in another branch?
                switch(terraintype) {
                    case '_': loadImg += "plain_ground.png";
                        break;
                    case '/': loadImg += "slant_ground_ri.png";
                        break;
                    case '\\':loadImg += "slant_ground_le.png";
                        break;
                    case '|': loadImg += "wall_le.png";
                        break;
                    case 'S': loadImg += "stones.png";
                        break;
                    case 'E': loadImg += "earth.png";
                        break;
                    case 'W': loadImg += "water.png";
                        break;
                    case 'P': // special case: spawn point, add to list and draw sky
                        spawnPoints.add(new Point2D(i, j));
                        terrain.get(i).set(j, ' ');
                    default : loadImg += "sky.png";
                }
                Image image = new Image(loadImg);
                ImageView content = new ImageView();
                content.setImage(image);

                add(content, j, i);
                //terrainGrid.setConstraints(content,j,i);
            }
        }
    }

    /**
     * @return the 2-D-ArrayList representing the loaded terrain
     */
    public ArrayList<ArrayList<Character>> toArrayList() {
        return terrain;
    }

    /**
     * get a spawn point and remove it from the list of available spawn points
     * @return a random spawn point, or null if there are no more spawn points
     */
    public Point2D getRandomSpawnPoint() {
        if(spawnPoints.isEmpty()) {
            return null;
        }
        int index = (int) (Math.random()*spawnPoints.size());
        Point2D spawnPoint = spawnPoints.get(index);
        spawnPoints.remove(index);
        System.out.println("TERRAIN: returning spawn point #" + index + " " + spawnPoint + " (" + spawnPoints.size() + " left)");
        return spawnPoint;
    }
}
