package skorlex.fogbegone.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skorlex.fogbegone.client.gui.FogBegoneOptionsScreen;

@Mixin(AccessibilityOptionsScreen.class)
public abstract class AccessibilityOptionsScreenMixin extends OptionsSubScreen {

    private AccessibilityOptionsScreenMixin() {
        super(null, null, null);
    }

    @Inject(method = "addOptions", at = @At("RETURN"))
    private void onAddOptionsReturn(CallbackInfo ci) {
        // Build the custom button
        Button customButton = Button.builder(Component.translatable("fogbegone.button"), (btn) -> {
            this.minecraft.gui.setScreen(new FogBegoneOptionsScreen(this, this.options));
        }).build();

        // Use addBig to make the button stretch across the full width, perfectly matching your screenshot arrow layout!
        this.list.addBig(customButton);
    }
}