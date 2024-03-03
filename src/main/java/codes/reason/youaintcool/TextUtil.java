package codes.reason.youaintcool;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public class TextUtil {

    private static final Text VIP_PREFIX = Text.empty()
            .append(Text.literal("[").withColor(0xFFAA00))
            .append(Text.literal("⭐")
                    .styled(style -> style
                            .withColor(0xFFD47F)
                            .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/vip"))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("VIP").withColor(0xFFD47F)))
                    ))
            .append(Text.literal("]").withColor(0xFFAA00));

    private static final Text VIP_WHOIS = Text.empty()
            .append(Text.literal("[").withColor(0xFFAA00))
            .append(Text.literal("VIP").withColor(0xFFD47F))
            .append(Text.literal("]").withColor(0xFFAA00));

    private static final Text FOUNDING_BADGE = Text.literal("⭐ ").styled(style -> style.withColor(0xFFD47F)
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Founding VIP").withColor(0xFFD47F))));

    private static final Text PLOT_AD_TEXT = Text.literal("Plot Ad").styled(style ->
            style.withColor(TextColor.fromFormatting(Formatting.YELLOW)));

    public static boolean isVIPTag(Text text) {
        return text.equals(VIP_PREFIX) || text.equals(VIP_WHOIS);
    }

    public static boolean isVIPBadge(Text text) {
        return text.equals(FOUNDING_BADGE);
    }

    public static boolean containsVIP(Text text) {
        return text.contains(VIP_PREFIX);
    }

    public static Text replaceVIPTag(Text text) {
        return replaceVIPTagInternal(text);
    }

    public static boolean isBoost(Text text) {
        if (!getContent(text).isEmpty()) {
            return false;
        }
        ClickEvent event = text.getStyle().getClickEvent();
        if (event == null) {
            return false;
        }
        if (event.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
            if (event.getValue().startsWith("/join")) {
                return !text.contains(PLOT_AD_TEXT);
            }
        }
        return false;
    }

    private static Text replaceVIPTagInternal(Text text) {
        YouAintCoolConfig coolConfig = YouAintCoolConfig.get();
        MutableText newText = MutableText.of(text.getContent())
                .setStyle(text.getStyle());

        boolean empty = getContent(text).isEmpty();
        boolean hideSpace = false;
        for (Text sibling : text.getSiblings()) {
            String content = getContent(sibling);
            if (hideSpace) {
                hideSpace = false;
                if (content.equals(" ")) {
                    continue;
                }
            }
            if (coolConfig.isHideTags() && isVIPTag(sibling)) {
                hideSpace = true;
                continue;
            }
            if (empty && coolConfig.isHideBadges() && isVIPBadge(sibling)) {
                continue;
            }
            newText.append(replaceVIPTagInternal(sibling));
        }
        return newText;
    }

    private static String getContent(Text text) {
        if (text.getContent() instanceof PlainTextContent content) {
            return content.string();
        }
        return "";
    }

}
