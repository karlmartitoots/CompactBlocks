package ee.kmtster.compact_blocks.config;

import ee.kmtster.compact_blocks.CompactBlocksPlugin;
import ee.kmtster.compact_blocks.model.CompactBlock;
import ee.kmtster.compact_blocks.model.CompactBlockFromBase64;
import ee.kmtster.compact_blocks.model.CompactBlockRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.Set;

import static ee.kmtster.compact_blocks.StringUtils.snakeCase;

public class ConfigLoader {

    private final CompactBlocksPlugin plugin;

    public ConfigLoader(CompactBlocksPlugin plugin) {
        this.plugin = plugin;
    }

    private void info(String message) {
        this.plugin.getLogger().info(message);
    }

    public void read(FileConfiguration conf, Map<NamespacedKey, CompactBlock> compacts) {
        Set<String> confKeys = conf.getKeys(false);

        int loadCount = 0;
        for (String key: confKeys) {
            Material material = Material.getMaterial(key);
            if (material == null) {
                info(String.format("Invalid key in config.yml: %s", key));
                continue;
            }

            String base64 = conf.getString(key);
            CompactBlock compactBlock;
            try {
                compactBlock = new CompactBlockFromBase64(material, base64);
            } catch (Throwable ignore) {
                info(String.format("Invalid value in config.yml under key: %s", key));
                continue;
            }

            CompactBlockRecipe compactRecipe = new CompactBlockRecipe(compactRecipeNamespacedKey(material), compactBlock);

            NamespacedKey inputKey = compactItemNamespacedKey(material);
            compacts.put(inputKey, compactBlock);

            Bukkit.addRecipe(compactRecipe.getRecipe());

            loadCount++;
        }

        info(String.format("Loaded %d blocks", loadCount));
    }

    private NamespacedKey compactItemNamespacedKey(Material material) {
        return new NamespacedKey(plugin, "compact_" + snakeCase(material.name()));
    }

    private NamespacedKey compactRecipeNamespacedKey(Material material) {
        return new NamespacedKey(plugin, "compact_" + snakeCase(material.name()) + "_recipe");
    }
}
