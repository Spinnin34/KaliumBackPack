package p.kaliumBackPack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.intellij.lang.annotations.Language;
import p.kaliumBackPack.Command.BackpackCommand;
import p.kaliumBackPack.Command.TabCompleter.BackpackTabCompleter;
import p.kaliumBackPack.Listener.BackpackListener;
import p.kaliumBackPack.Manager.BackpackManager;
import p.kaliumBackPack.Manager.DataBase.SQLiteDatabase;
import p.kaliumBackPack.Manager.RoleManager;
import p.kaliumBackPack.Utils.LangUtil;

import java.sql.SQLException;

public final class KaliumBackPack extends JavaPlugin {

    private BackpackManager backpackManager;
    private SQLiteDatabase database;
    private static KaliumBackPack instance;
    private LangUtil langUtil;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        setupConfig();

        database = new SQLiteDatabase("plugins/KaliumBackPack/backpacks.db");
        try {
            database.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RoleManager role = new RoleManager(this);
        backpackManager = new BackpackManager(database, role);

        getServer().getPluginManager().registerEvents(new BackpackListener(backpackManager), this);
        getCommand("pv").setExecutor(new BackpackCommand(this));
        getCommand("pv").setTabCompleter(new BackpackTabCompleter(role));
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(player -> backpackManager.savePlayerBackpacks(player));

        if (database != null) {
            database.disconnect();
        }
    }

    public void setupConfig() {
        langUtil = new LangUtil(this);
        langUtil.saveDefaultLang();
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }


    public static KaliumBackPack getInstance() {
        return instance;
    }
}
