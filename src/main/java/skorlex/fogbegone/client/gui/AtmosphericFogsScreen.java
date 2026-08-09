package skorlex.fogbegone.client.gui;

import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import skorlex.fogbegone.util.ModOptions;

public class AtmosphericFogsScreen extends OptionsSubScreen {

    // Added the explicit constructor to receive the screen and options parameters
    public AtmosphericFogsScreen(Screen lastScreen, Options options) {
        super(lastScreen, options, Component.translatable("fogbegone.options.atmospheric.title"));
    }

    @Override
    protected void addOptions() {
        this.list.addSmall(
                ModOptions.RENDER_OVERWORLD_FOG,
                ModOptions.RENDER_NETHER_FOG
        );
        this.list.addSmall(
                ModOptions.RENDER_END_FOG,
                ModOptions.RENDER_BORDER_FOG
        );
    }
}