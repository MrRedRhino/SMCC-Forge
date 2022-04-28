package org.pipeman.smcc.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.checkerframework.checker.units.qual.A;
import org.pipeman.smcc.IGetMessages;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Unique
@Mixin(ChatComponent.class)
public abstract class ChatComponentMixin implements IGetMessages {

    @Mutable
    @Shadow @Final private List<GuiMessage<Component>> allMessages;

    @Shadow public abstract void clearMessages(boolean p_93796_);

    @Shadow @Final private List<GuiMessage<FormattedCharSequence>> trimmedMessages;

    @Override
    public List<GuiMessage<Component>> getMessages() {
        return this.allMessages;
    }

    @Override
    public void setMessages(List<GuiMessage<Component>> messages) {
        clearMessages(false);
        allMessages = messages;
    }

    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;IIZ)V", at = @At("TAIL"))
    private void addMessage(Component p_93791_, int p_93792_, int p_93793_, boolean p_93794_, CallbackInfo ci) {
        // for (GuiMessage<FormattedCharSequence> msg : this.trimmedMessages) {
        //     System.out.println(msg.getId());
        // }
    }
}
