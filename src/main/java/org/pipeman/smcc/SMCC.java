package org.pipeman.smcc;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pipeman.smcc.gui.SettingsScreenHotkeyHandler;
import org.pipeman.smcc.options.Options;


@Mod("smcc")
public class SMCC {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Minecraft MC = Minecraft.getInstance();
    public static Encryptor encryptor;

    public SMCC() {
        MinecraftForge.EVENT_BUS.register(new ChatEventListener());
        MinecraftForge.EVENT_BUS.register(new SettingsScreenHotkeyHandler());
        ClientRegistry.registerKeyBinding(SettingsScreenHotkeyHandler.openerKey);

        Options.readConfigFile();
        encryptor = new Encryptor(Options.key);
    }
}
