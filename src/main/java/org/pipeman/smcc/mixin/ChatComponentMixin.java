package org.pipeman.smcc.mixin;

import net.minecraft.client.GuiMessage;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.pipeman.smcc.IGetMessages;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Unique
@Mixin(ChatComponent.class)
public abstract class ChatComponentMixin implements IGetMessages {

    @Mutable
    @Shadow @Final private List<GuiMessage<Component>> allMessages;

    @Shadow public abstract void clearMessages(boolean p_93796_);

    @Override
    public List<GuiMessage<Component>> getMessages() {
        return this.allMessages;
    }

    @Override
    public void setMessages(List<GuiMessage<Component>> messages) {
        clearMessages(false);
        allMessages = messages;
    }
}
