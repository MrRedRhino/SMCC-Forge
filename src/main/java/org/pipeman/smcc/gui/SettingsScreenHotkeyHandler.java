package org.pipeman.smcc.gui;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SettingsScreenHotkeyHandler {
    public static final KeyMapping openerKey = new KeyMapping("Settings", 66, "SMCC");

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (openerKey.isDown()) {
            Minecraft.getInstance().setScreen(new SmccSettingsScreen(Component.nullToEmpty("SMCC Settings")));
        }
    }
}
