package org.pipeman.smcc;

import net.minecraft.client.GuiMessage;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IGetMessages {
    List<GuiMessage<Component>> getMessages();

    void setMessages(List<GuiMessage<Component>> messages);
}
