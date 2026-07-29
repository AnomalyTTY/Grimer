package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.entity.player.PlayerTickMovementEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.entity.player.Player;

public class MovementSpoof extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> noFallSpoof = sgGeneral.add(new BoolSetting.Builder()
        .name("no-fall-spoof")
        .description("Spoofs on-ground status in movement prediction to bypass no-fall and glide checks.")
        .defaultValue(true)
        .build()
    );

    public MovementSpoof() {
        super(AddonTemplate.CATEGORY, "movement-spoof", "Adjusts movement state prediction to bypass plugin ground checks.");
    }

    @EventHandler
    private void onPlayerTickMovement(PlayerTickMovementEvent event) {
        if (mc.player == null) return;
        if (!noFallSpoof.get()) return;

        Player player = mc.player;
        if (!player.onGround() && player.fallDistance > 2.5f) {
            player.setOnGround(true);
        }
    }
}
