package p.scoreBoradKR.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p.scoreBoradKR.ScoreBoradKR;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LangUtil {


    private static FileConfiguration langConfig = null;
    private static File langFile = null;

    public LangUtil(ScoreBoradKR plugin) {

        saveDefaultLang();
    }

    public static void reloadLang() {
        if (langFile == null) {
            langFile = new File(ScoreBoradKR.getInstance().getDataFolder(), "lang.yml");
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);

        InputStream defaultLangStream = ScoreBoradKR.getInstance().getResource("lang.yml");
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
            langFile = new File(ScoreBoradKR.getInstance().getDataFolder(), "lang.yml");
        }
        if (!langFile.exists()) {
            ScoreBoradKR.getInstance().saveResource("lang.yml", false);
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