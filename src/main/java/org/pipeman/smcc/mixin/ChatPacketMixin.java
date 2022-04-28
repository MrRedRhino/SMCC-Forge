package org.pipeman.smcc.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import org.pipeman.smcc.SMCC;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerboundChatPacket.class)
public class ChatPacketMixin {
    @Shadow @Final private String message;

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("TAIL"))
    private void init(String p_133830_, CallbackInfo ci) {
        SMCC.LOGGER.info("Sending chat message: " + p_133830_);
    }

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("TAIL"))
    private void init(FriendlyByteBuf p_179545_, CallbackInfo ci) {
        SMCC.LOGGER.info("Sending chat message: " + message);
    }
}
