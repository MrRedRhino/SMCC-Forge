package org.pipeman.smcc;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.StrongTextEncryptor;

public class Encryptor {
    private final StrongTextEncryptor encryptor;
    private String pwd;

    public Encryptor(String pwd) {
        encryptor = new StrongTextEncryptor();
        pwd(pwd);

    }

    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }

    public void pwd(String newPwd) {
        encryptor.setPassword(newPwd);
        pwd = newPwd;
    }

    public TranslatableComponent processMessage(TranslatableComponent msg) {
        if (msg.getKey().equals("chat.type.text") ||
                msg.getKey().equals("commands.message.display.incoming") ||
                msg.getKey().equals("commands.message.display.outgoing") ||
                msg.getKey().equals("chat.type.team.sent") ||
                msg.getKey().equals("chat.type.team.received")) { // TODO Team receive

            boolean isTeamMsg = msg.getKey().equals("chat.type.team.sent") ||
                    msg.getKey().equals("chat.type.team.received"); // TODO Team receive

            try {
                Object[] args = msg.getArgs();
                String decryptedMsg;
                if (args[isTeamMsg ? 2 : 1] instanceof String s) {
                    decryptedMsg = SMCC.encryptor.decrypt(s);
                } else {
                    decryptedMsg = SMCC.encryptor.decrypt(((TextComponent) args[isTeamMsg ? 2 : 1]).getText());
                }

                Object[] newArgs;
                if (isTeamMsg) {
                    newArgs = new Object[]{args[0], args[1], decryptedMsg};
                } else {
                    newArgs = new Object[]{args[0], decryptedMsg};
                }

                TranslatableComponent newComp = new TranslatableComponent(msg.getKey(), newArgs);
                newComp.setStyle(msg.getStyle());
                return newComp;
            } catch (EncryptionOperationNotPossibleException | EncryptionInitializationException ignored) {}
        }
        return msg;
    }

    public String pwd() {
        return pwd;
    }
}
