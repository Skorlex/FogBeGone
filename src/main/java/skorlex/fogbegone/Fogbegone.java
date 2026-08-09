package skorlex.fogbegone;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skorlex.fogbegone.client.gui.FogBegoneOptionsScreen;
import skorlex.fogbegone.config.ConfigManager;

public class Fogbegone implements ClientModInitializer {
	public static final String MOD_ID = "fogbegone";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Register the custom category
	public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(MOD_ID, "settings_category")
	);

	// Register the key mapping using the updated KeyMappingHelper
	private static final KeyMapping openSettingsKey = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"key.fogbegone.open_settings", // The translation key for the key mapping
					InputConstants.Type.KEYSYM,    // The type of the keybinding
					InputConstants.KEY_F9,         // The default keycode (F9)
					CATEGORY                       // The category of the mapping
			)
	);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing FogBeGone on the client...");

		// Load the configuration file as soon as the client starts
		ConfigManager.load();

		// Listen for the key press every client tick
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openSettingsKey.consumeClick()) {
				// Read the current screen via gui.screen() and open the new one via gui.setScreen()
				client.gui.setScreen(new FogBegoneOptionsScreen(client.gui.screen(), client.options));
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}