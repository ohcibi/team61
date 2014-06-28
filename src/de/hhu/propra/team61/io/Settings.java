package de.hhu.propra.team61.io;

import de.hhu.propra.team61.io.json.JSONObject;

/**
 * Saves game settings.
 *
 * Created by Jessypet on 24.05.14.
 */
public class Settings {

    /**
     * @param json is saved to SETTINGS_FILE
     */
    public static void save(JSONObject json, String file) {
        file = file+".conf";
        Json.save(json, file);
    }

    /**
     * @return game state loaded from SAVE_STATE_FILE
     */
    public static JSONObject getSavedSettings(String loadfilestring) {
        return Json.getFromFile(loadfilestring);
    }

}
