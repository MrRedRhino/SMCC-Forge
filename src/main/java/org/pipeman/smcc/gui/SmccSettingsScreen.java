package org.pipeman.smcc.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.*;
import org.pipeman.smcc.SMCC;
import org.pipeman.smcc.options.Options;

import static org.pipeman.smcc.SMCC.MC;

public class SmccSettingsScreen extends Screen {
    EditBox passwordInput;
    Button encryptNormalChatButton;
    Button encryptTeamMessagesButton;
    Button encryptDMsButton;
    protected SmccSettingsScreen(Component p_96550_) {
        super(p_96550_);
    }

    @Override
    protected void init() {
        passwordInput = new EditBox(MC.font, 10, 10, 100, 10, Component.nullToEmpty("yep"));
        this.addWidget(passwordInput);
        MC.keyboardHandler.setSendRepeatsToGui(true);
        passwordInput.setValue(Options.key);

        // buttons
        encryptNormalChatButton = new Button(10, 35, 180, 20,
                getToggleButtonContent("Encrypt normal chat: ", Options.encryptNormalChat), p_93751_ -> {
            Options.encryptNormalChat = !Options.encryptNormalChat;
            p_93751_.setMessage(getToggleButtonContent("Encrypt normal chat: ", Options.encryptNormalChat));
        });
        this.addWidget(encryptNormalChatButton);

        encryptDMsButton = new Button(10, 60, 180, 20,
                getToggleButtonContent("Encrypt direct messages: ", Options.encryptTell), p_93751_ -> {
            Options.encryptTell = !Options.encryptTell;
            p_93751_.setMessage(getToggleButtonContent("Encrypt direct messages: ", Options.encryptTell));
        });
        this.addWidget(encryptDMsButton);

        encryptTeamMessagesButton = new Button(10, 85, 180, 20,
                getToggleButtonContent("Encrypt team messages: ", Options.encryptTeamMessages), p_93751_ -> {
            Options.encryptTeamMessages = !Options.encryptTeamMessages;
            p_93751_.setMessage(getToggleButtonContent("Encrypt team messages: ", Options.encryptTeamMessages));
        });
        this.addWidget(encryptTeamMessagesButton);
    }

    private Component getToggleButtonContent(String name, boolean toggledOn) {
        return new TextComponent(name).append(
                new TextComponent(toggledOn ? "ON" : "OFF").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(
                        toggledOn ? 0x00FF00 : 0xFF0000))));
    }

    @Override
    public void render(PoseStack poseStack, int p_95596_, int p_95597_, float p_95598_) {
        fill(poseStack, 2, this.height - 14, this.width - 2, this.height - 2, 0xFFFFFF);
        passwordInput.render(poseStack, p_95596_, p_95597_, p_95598_);
        encryptNormalChatButton.render(poseStack, p_95596_, p_95597_, p_95598_);
        encryptDMsButton.render(poseStack, p_95596_, p_95597_, p_95598_);
        encryptTeamMessagesButton.render(poseStack, p_95596_, p_95597_, p_95598_);

        passwordInput.setSuggestion(passwordInput.getValue().isEmpty() && !passwordInput.isFocused() ? "Password" : "");

        super.render(poseStack, p_95596_, p_95597_, p_95598_);
    }

    @Override
    public void resize(Minecraft p_95600_, int p_95601_, int p_95602_) {
        passwordInput.x = p_95601_ / 2 - passwordInput.getWidth() / 2;
        passwordInput.y = p_95602_ / 2 - passwordInput.getHeight() / 2;
    }

    @Override
    public void onClose() {
        super.onClose();
        Options.key = passwordInput.getValue();
        SMCC.encryptor.setPwd(Options.key);
        Options.storeConfigFile();
//        List<GuiMessage<Component>> messages = ((IGetMessages) MC.gui.getChat()).getMessages();
//        List<Component> newMessages = new ArrayList<>();
//
//        for (GuiMessage<Component> msg : messages) {
//            if (msg.getMessage() instanceof TranslatableComponent comp) {
//                newMessages.add(SMCC.encryptor.processMessage(comp)); // new GuiMessage<>(addedTime, newComp, id)
//            }
//        }
//
//        MC.gui.getChat().clearMessages(false);
//
//        Collections.reverse(newMessages);
//        newMessages.forEach(MC.gui.getChat()::addMessage);
    }
}
