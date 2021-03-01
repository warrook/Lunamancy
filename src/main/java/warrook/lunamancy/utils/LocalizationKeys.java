package warrook.lunamancy.utils;

import net.minecraft.text.TranslatableText;
import warrook.lunamancy.Lunamancy;

public final class LocalizationKeys {

    //GUI
    public static final LocalizationKey GUI_NET_CONNECTING = build("gui", "net.connecting");

    //Basic
    public static final LocalizationKey ITEM = build("item");
    public static final LocalizationKey BLOCK = build("block");

    private static LocalizationKey build(String prefix, String suffix) {
        return new LocalizationKey(prefix, suffix);
    }

    private static LocalizationKey build(String prefix) {
        return new LocalizationKey(prefix);
    }

    public static final class LocalizationKey {
        private final String prefix, suffix;

        public LocalizationKey(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public LocalizationKey(String prefix) {
            this.prefix = prefix;
            this.suffix = null;
        }

        public String get() {
            String s = prefix + "." + Lunamancy.MOD_ID + ".";
            return suffix != null ? s + suffix : s;
        }

        public String of(String string) {
            return get() + "." + string;
        }

        public String translate(String string) {
            return new TranslatableText(of(string)).getString();
        }
    }
}
