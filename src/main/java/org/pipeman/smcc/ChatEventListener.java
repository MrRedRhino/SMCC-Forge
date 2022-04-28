package org.pipeman.smcc;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.pipeman.smcc.options.Options;

import java.util.Arrays;

import static org.pipeman.smcc.SMCC.MC;

public class ChatEventListener {
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        if (MC.player == null) return;
        if (event.getMessage() instanceof TranslatableComponent comp) {
            event.setMessage(SMCC.encryptor.processMessage(comp));
        }
    }

    @SubscribeEvent
    public void onChatMessage(ClientChatEvent event) {
        String msg = event.getMessage();
        if (isNormalChat(msg) && Options.encryptNormalChat) {
            event.setCanceled(true);
            sendChatMessageAndAddToHistory(SMCC.encryptor.encrypt(msg), msg);
        }

        if (isTell(msg) && Options.encryptTell) {
            String[] splitMsg = msg.split(" ");
            String msgToEncrypt = String.join(" ", Arrays.copyOfRange(splitMsg, 2, splitMsg.length));
            String msgToKeep = String.join(" ", Arrays.copyOfRange(splitMsg, 0, 2));

            if (!msgToEncrypt.isEmpty()) {
                event.setCanceled(true);
                sendChatMessageAndAddToHistory(msgToKeep + " " + SMCC.encryptor.encrypt(msgToEncrypt), msg);
            }
        }

        if (isTeamMessage(msg) && Options.encryptTeamMessages) {
            String[] splitMsg = msg.split(" ");
            String msgToEncrypt = String.join(" ", Arrays.copyOfRange(splitMsg, 1, splitMsg.length));

            if (!msgToEncrypt.isEmpty()) {
                event.setCanceled(true);
                sendChatMessageAndAddToHistory("/tm " + SMCC.encryptor.encrypt(msgToEncrypt), msg);
            }
        }
    }

    private void sendChatMessageAndAddToHistory(String msgToSend, String originalMsg) {
        if (MC.getConnection() == null) return;
        MC.getConnection().send(new ServerboundChatPacket(msgToSend));
        MC.gui.getChat().addRecentChat(originalMsg);
    }

    private boolean isTell(String msg) {
        return msg.startsWith("/tell") || msg.startsWith("/w") || msg.startsWith("/msg");
    }

    private boolean isTeamMessage(String msg) {
        return msg.startsWith("/teammsg") || msg.startsWith("/tm");
    }

    private boolean isNormalChat(String msg) {
        return !msg.startsWith("/");
    }
}
