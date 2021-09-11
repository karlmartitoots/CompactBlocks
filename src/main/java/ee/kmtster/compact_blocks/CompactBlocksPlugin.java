package ee.kmtster.compact_blocks;

import ee.kmtster.compact_blocks.config.ConfigLoader;
import ee.kmtster.compact_blocks.listeners.UncompactListener;
import ee.kmtster.compact_blocks.model.CompactBlock;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

public final class CompactBlocksPlugin extends JavaPlugin {
    public final static Map<NamespacedKey, CompactBlock> compacts = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        this.loadConfig();
        ConfigLoader config = new ConfigLoader(this);
        config.read(getConfig(), compacts);

        Bukkit.getPluginManager().registerEvents( new UncompactListener(this), this);

        getServer().getConsoleSender().sendMessage("Enabled CompactBlocks plugin.");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("To disable CompactBlocks plugin remove the plugin jar.");
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
