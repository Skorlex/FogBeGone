package skorlex.fogbegone.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.Minecraft;
import skorlex.fogbegone.client.gui.FogBegoneOptionsScreen;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new FogBegoneOptionsScreen(parent, Minecraft.getInstance().options);
    }
}