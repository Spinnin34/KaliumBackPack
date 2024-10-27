package p.kaliumBackPack.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p.kaliumBackPack.KaliumBackPack;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LangUtil {


    private static FileConfiguration langConfig = null;
    private static File langFile = null;

    public LangUtil(KaliumBackPack plugin) {

        saveDefaultLang();
    }

    public static void reloadLang() {
        if (langFile == null) {
            langFile = new File(KaliumBackPack.getInstance().getDataFolder(), "lang.yml");
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);

        InputStream defaultLangStream = KaliumBackPack.getInstance().getResource("lang.yml");
        if (defaultLangStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultLangStream));
            langConfig.setDefaults(defaultConfig);
        }
    }

    public static FileConfiguration getLang() {
        if (langConfig == null) {
            reloadLang();
        }
        return langConfig;
    }

    public void saveDefaultLang() {
        if (langFile == null) {
            langFile = new File(KaliumBackPack.getInstance().getDataFolder(), "lang.yml");
        }
        if (!langFile.exists()) {
            KaliumBackPack.getInstance().saveResource("lang.yml", false);
        }
    }

    public static String getString(String path, boolean usePrefix) {
        String rawString = getLang().getString(path);

        if (rawString == null) {
            return "Texto por defecto para " + path;
        }

        if (usePrefix) {
            String prefix = getLang().getString("messages.prefix", "");
            rawString = prefix + rawString;
        }

        return ColoredStringUtil.colorHex(rawString);
    }
}