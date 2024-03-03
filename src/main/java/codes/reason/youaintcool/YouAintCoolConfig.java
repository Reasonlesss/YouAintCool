package codes.reason.youaintcool;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "youaintcool")
public class YouAintCoolConfig implements ConfigData {

    boolean hideTags = true;
    boolean hideBadges = true;
    boolean hideBoosts = true;
    boolean silenceMode = false; // why

    public static YouAintCoolConfig get() {
        return AutoConfig.getConfigHolder(YouAintCoolConfig.class).getConfig();
    }

    public boolean isHideTags() {
        return hideTags;
    }

    public boolean isHideBadges() {
        return hideBadges;
    }

    public boolean isHideBoosts() {
        return hideBoosts;
    }

    public boolean isSilenceMode() {
        return silenceMode;
    }

}
