package codes.reason.youaintcool.mixin;

import codes.reason.youaintcool.TextUtil;
import codes.reason.youaintcool.YouAintCoolConfig;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public Text inject(Text message) {
        if (!YouAintCoolConfig.get().isHideTags() && !YouAintCoolConfig.get().isHideBadges()) {
            return message;
        }
        return TextUtil.replaceVIPTag(message);
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), cancellable = true)
    public void addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (YouAintCoolConfig.get().isSilenceMode() && TextUtil.containsVIP(message)) {
            ci.cancel();
            return;
        }
        if (YouAintCoolConfig.get().isHideBoosts() && TextUtil.isBoost(message)) {
            ci.cancel();
            // the sound apparently occurs before the boost, will investigate.
//            SoundHider.hide();
        }
    }

}
