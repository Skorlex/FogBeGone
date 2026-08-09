package skorlex.fogbegone.util;

import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import skorlex.fogbegone.config.ConfigManager;

public class ModOptions {

    public static final OptionInstance<Integer> UNDERWATER_FOG_OPACITY = new OptionInstance<>(
            "fogbegone.option.underwater",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.underwater")),
            (caption, value) -> {
                if (value == 0) return Component.empty().append(caption).append(": ").append(Component.translatable("options.off"));
                if (value == 100) return Component.empty().append(caption).append(": Default");
                return Component.empty().append(caption).append(": ").append(value.toString()).append("%");
            },
            new OptionInstance.IntRange(0, 100),
            100,
            (val) -> ConfigManager.save()
    );

    public static final OptionInstance<Integer> LAVA_FOG_OPACITY = new OptionInstance<>(
            "fogbegone.option.lava",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.lava")),
            (caption, value) -> {
                if (value == 0) return Component.empty().append(caption).append(": ").append(Component.translatable("options.off"));
                if (value == 100) return Component.empty().append(caption).append(": Default");
                return Component.empty().append(caption).append(": ").append(value.toString()).append("%");
            },
            new OptionInstance.IntRange(0, 100),
            100,
            (val) -> ConfigManager.save()
    );

    public static final OptionInstance<Boolean> RENDER_DARKNESS_FOG = OptionInstance.createBoolean(
            "fogbegone.option.darkness",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.darkness")),
            (caption, value) -> value ? Component.literal("Default") : Component.translatable("options.off"),
            true,
            (val) -> ConfigManager.save()
    );

    public static final OptionInstance<Boolean> RENDER_BLINDNESS_FOG = OptionInstance.createBoolean(
            "fogbegone.option.blindness",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.blindness")),
            (caption, value) -> value ? Component.literal("Default") : Component.translatable("options.off"),
            true,
            (val) -> ConfigManager.save()
    );

    public static final OptionInstance<Boolean> RENDER_OVERWORLD_FOG = OptionInstance.createBoolean(
            "fogbegone.option.overworld",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.overworld")),
            (caption, value) -> value ? Component.literal("Default") : Component.translatable("options.off"),
            true,
            (val) -> ConfigManager.save()
    );

    public static final OptionInstance<Boolean> RENDER_NETHER_FOG = OptionInstance.createBoolean(
            "fogbegone.option.nether",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.nether")),
            (caption, value) -> value ? Component.literal("Default") : Component.translatable("options.off"),
            true,
            (val) -> ConfigManager.save()
    );

    public static final OptionInstance<Boolean> RENDER_END_FOG = OptionInstance.createBoolean(
            "fogbegone.option.end",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.end")),
            (caption, value) -> value ? Component.literal("Default") : Component.translatable("options.off"),
            true,
            (val) -> ConfigManager.save()
    );

    public static final OptionInstance<Boolean> RENDER_BORDER_FOG = OptionInstance.createBoolean(
            "fogbegone.option.border",
            OptionInstance.cachedConstantTooltip(Component.translatable("fogbegone.tooltip.border")),
            (caption, value) -> value ? Component.literal("Default") : Component.translatable("options.off"),
            true,
            (val) -> ConfigManager.save()
    );
}