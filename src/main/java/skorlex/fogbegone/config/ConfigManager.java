package skorlex.fogbegone.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import skorlex.fogbegone.util.ModOptions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    // We use GsonBuilder with setPrettyPrinting so the JSON file is readable for players
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Locates the Minecraft 'config' folder and targets 'fogbegone.json'
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("fogbegone.json").toFile();

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                JsonObject json = GSON.fromJson(reader, JsonObject.class);

                if (json == null) {
                    save();
                    return;
                }

                // Check if the property exists in the JSON, and if so, update the OptionInstance
                if (json.has("underwaterFogOpacity")) ModOptions.UNDERWATER_FOG_OPACITY.set(json.get("underwaterFogOpacity").getAsInt());
                if (json.has("lavaFogOpacity")) ModOptions.LAVA_FOG_OPACITY.set(json.get("lavaFogOpacity").getAsInt());
                if (json.has("renderDarknessFog")) ModOptions.RENDER_DARKNESS_FOG.set(json.get("renderDarknessFog").getAsBoolean());
                if (json.has("renderBlindnessFog")) ModOptions.RENDER_BLINDNESS_FOG.set(json.get("renderBlindnessFog").getAsBoolean());
                if (json.has("renderOverworldFog")) ModOptions.RENDER_OVERWORLD_FOG.set(json.get("renderOverworldFog").getAsBoolean());
                if (json.has("renderNetherFog")) ModOptions.RENDER_NETHER_FOG.set(json.get("renderNetherFog").getAsBoolean());
                if (json.has("renderEndFog")) ModOptions.RENDER_END_FOG.set(json.get("renderEndFog").getAsBoolean());
                if (json.has("renderBorderFog")) ModOptions.RENDER_BORDER_FOG.set(json.get("renderBorderFog").getAsBoolean());

            } catch (Exception e) {
                System.err.println("Failed to load FogBeGone config! Reverting to defaults.");
                e.printStackTrace();
            }
        } else {
            // Generate the file with default values if it doesn't exist yet
            save();
        }
    }

    public static void save() {
        JsonObject json = new JsonObject();

        // Grab the current values from the OptionInstances and add them to the JSON object
        json.addProperty("underwaterFogOpacity", ModOptions.UNDERWATER_FOG_OPACITY.get());
        json.addProperty("lavaFogOpacity", ModOptions.LAVA_FOG_OPACITY.get());
        json.addProperty("renderDarknessFog", ModOptions.RENDER_DARKNESS_FOG.get());
        json.addProperty("renderBlindnessFog", ModOptions.RENDER_BLINDNESS_FOG.get());
        json.addProperty("renderOverworldFog", ModOptions.RENDER_OVERWORLD_FOG.get());
        json.addProperty("renderNetherFog", ModOptions.RENDER_NETHER_FOG.get());
        json.addProperty("renderEndFog", ModOptions.RENDER_END_FOG.get());
        json.addProperty("renderBorderFog", ModOptions.RENDER_BORDER_FOG.get());

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            System.err.println("Failed to save FogBeGone config!");
            e.printStackTrace();
        }
    }
}