package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class BypassUtilities extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> pluginMessageSpoof = sgGeneral.add(new BoolSetting.Builder()
        .name("plugin-message-spoof")
        .description("Spoof plugin message responses and bypass simple plugin checks.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> antiCheatMarkSpoof = sgGeneral.add(new BoolSetting.Builder()
        .name("anti-cheat-mark-spoof")
        .description("Spoof anti-cheat status and delay suspicious movement data.")
        .defaultValue(true)
        .build()
    );

    public BypassUtilities() {
        super(AddonTemplate.CATEGORY, "bypass-utilities", "Utility bypasses that modify client-side state for anarchy server checks.");
    }

    @Override
    public void onActivate() {
        if (pluginMessageSpoof.get()) {
            info("Plugin message spoof enabled.");
        }
    }
}
