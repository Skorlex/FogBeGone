package skorlex.fogbegone.client.gui;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import skorlex.fogbegone.util.ModOptions;

public class FogBegoneOptionsScreen extends OptionsSubScreen {

    public FogBegoneOptionsScreen(Screen lastScreen, Options options) {
        super(lastScreen, options, Component.translatable("fogbegone.options.title"));
    }

    @Override
    protected void addOptions() {
        // Place Sliders side-by-side
        this.list.addSmall(
                ModOptions.UNDERWATER_FOG_OPACITY,
                ModOptions.LAVA_FOG_OPACITY
        );

        // Place Effect toggles side-by-side on the next row
        this.list.addSmall(
                ModOptions.RENDER_DARKNESS_FOG,
                ModOptions.RENDER_BLINDNESS_FOG
        );

        // Build our custom action button
        Button atmosphericButton = Button.builder(Component.translatable("fogbegone.button.atmospheric"), (btn) -> {
            this.minecraft.gui.setScreen(new AtmosphericFogsScreen(this, this.options));
        }).build();

        // Wrap it in a Java List to bypass the strict OptionInstance requirement and insert it directly into the scrolling menu!
        this.list.addSmall(java.util.List.of(atmosphericButton));
    }
}