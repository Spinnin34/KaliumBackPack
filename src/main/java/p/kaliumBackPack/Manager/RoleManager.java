package p.kaliumBackPack.Manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import p.kaliumBackPack.KaliumBackPack;
import p.kaliumBackPack.Model.Role;

import java.util.HashMap;
import java.util.Map;

public class RoleManager {
    private final Map<String, Role> roles = new HashMap<>();
    private final KaliumBackPack plugin;

    public RoleManager(KaliumBackPack plugin) {
        this.plugin = plugin;
        loadRolesFromConfig();
    }

    private void loadRolesFromConfig() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("roles");
        if (section != null) {
            for (String roleName : section.getKeys(false)) {
                int rows = section.getInt(roleName + ".rows");
                int maxBackpacks = section.getInt(roleName + ".max_backpacks");
                roles.put(roleName, new Role(roleName, rows, maxBackpacks));
            }
        }
    }

    public Role getRoleForPlayer(Player player) {
        Role highestRole = null;

        for (Map.Entry<String, Role> entry : roles.entrySet()) {
            String roleName = entry.getKey();
            String permission = "kalium." + roleName + ".backpack";

            if (player.hasPermission(permission)) {
                if (highestRole == null || roles.get(highestRole.getName()).getRows() < entry.getValue().getRows()) {
                    highestRole = entry.getValue();
                }
            }
        }

        return highestRole != null ? highestRole : roles.get("default");
    }

}

