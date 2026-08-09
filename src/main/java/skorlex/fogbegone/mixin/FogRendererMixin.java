package skorlex.fogbegone.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skorlex.fogbegone.util.ModOptions;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Inject(method = "getFogType", at = @At("RETURN"), cancellable = true)
    private void onGetFogType(Camera camera, CallbackInfoReturnable<FogType> cir) {
        FogType current = cir.getReturnValue();
        if (current == FogType.WATER && ModOptions.UNDERWATER_FOG_OPACITY.get() == 0) {
            cir.setReturnValue(FogType.ATMOSPHERIC);
        } else if (current == FogType.LAVA && ModOptions.LAVA_FOG_OPACITY.get() == 0) {
            cir.setReturnValue(FogType.ATMOSPHERIC);
        }
    }

    @Redirect(
            method = {"setupFog", "computeFogColor"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;isApplicable(Lnet/minecraft/world/level/material/FogType;Lnet/minecraft/world/entity/Entity;)Z")
    )
    private boolean redirectIsApplicable(FogEnvironment instance, FogType fogType, Entity entity) {
        String className = instance.getClass().getSimpleName();

        if (className.contains("Blindness") && !ModOptions.RENDER_BLINDNESS_FOG.get()) {
            return false;
        }
        if (className.contains("Darkness") && !ModOptions.RENDER_DARKNESS_FOG.get()) {
            return false;
        }

        return instance.isApplicable(fogType, entity);
    }

    @Inject(method = "setupFog", at = @At("RETURN"))
    private void onSetupFogReturn(Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir) {
        FogData fog = cir.getReturnValue();
        if (fog == null) return;

        // Apply Global Border Fog Setting
        if (!ModOptions.RENDER_BORDER_FOG.get()) {
            fog.renderDistanceStart = Float.MAX_VALUE;
            fog.renderDistanceEnd = Float.MAX_VALUE;
        }

        FogType baseFogType = camera.getFluidInCamera();
        Entity entity = camera.entity();

        if (baseFogType == FogType.LAVA) {
            int opacity = ModOptions.LAVA_FOG_OPACITY.get();
            if (opacity > 0 && opacity < 100) {
                float multiplier = 100.0f / opacity;
                fog.environmentalStart *= multiplier;
                fog.environmentalEnd *= multiplier;
            }
            return;
        }

        if (baseFogType == FogType.POWDER_SNOW) return;

        if (entity instanceof LivingEntity living) {
            if (living.hasEffect(MobEffects.BLINDNESS) && ModOptions.RENDER_BLINDNESS_FOG.get()) return;
            if (living.hasEffect(MobEffects.DARKNESS) && ModOptions.RENDER_DARKNESS_FOG.get()) return;
        }

        if (baseFogType == FogType.WATER) {
            int opacity = ModOptions.UNDERWATER_FOG_OPACITY.get();
            if (opacity > 0 && opacity < 100) {
                float multiplier = 100.0f / opacity;
                fog.environmentalStart *= multiplier;
                fog.environmentalEnd *= multiplier;
            }
            return;
        }

        boolean shouldDisableAtmospheric = false;

        if (level.dimension() == Level.OVERWORLD && !ModOptions.RENDER_OVERWORLD_FOG.get()) {
            shouldDisableAtmospheric = true;
        } else if (level.dimension() == Level.NETHER && !ModOptions.RENDER_NETHER_FOG.get()) {
            shouldDisableAtmospheric = true;
        } else if (level.dimension() == Level.END && !ModOptions.RENDER_END_FOG.get()) {
            shouldDisableAtmospheric = true;
        }

        if (shouldDisableAtmospheric) {
            fog.environmentalStart = Float.MAX_VALUE;
            fog.environmentalEnd = Float.MAX_VALUE;
        }
    }
}