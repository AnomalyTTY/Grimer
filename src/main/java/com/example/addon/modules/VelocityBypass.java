package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

public class VelocityBypass extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> horizontalFactor = sgGeneral.add(new DoubleSetting.Builder()
        .name("horizontal-factor")
        .description("Scale horizontal movement to reduce knockback and bypass velocity checks.")
        .defaultValue(0.2)
        .range(0.0, 1.0)
        .build()
    );

    private final Setting<Double> verticalFactor = sgGeneral.add(new DoubleSetting.Builder()
        .name("vertical-factor")
        .description("Scale vertical movement after velocity hits to stay under anti-knockback limits.")
        .defaultValue(0.0)
        .range(0.0, 1.0)
        .build()
    );

    private final Setting<Boolean> onlyWhenHurt = sgGeneral.add(new BoolSetting.Builder()
        .name("only-when-hurt")
        .description("Only dampen movement when the player was recently hurt or knocked back.")
        .defaultValue(true)
        .build()
    );

    public VelocityBypass() {
        super(AddonTemplate.CATEGORY, "velocity-bypass", "Dampens client movement and knockback to bypass GrimAC velocity prediction.");
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if (mc.player == null || event.movement == null) return;
        if (event.type != MoverType.SELF) return;
        if (onlyWhenHurt.get() && mc.player.hurtTime == 0) return;

        event.movement = new Vec3(
            event.movement.x * horizontalFactor.get(),
            event.movement.y * verticalFactor.get(),
            event.movement.z * horizontalFactor.get()
        );
    }
}
