package ee.kmtster.compact_blocks;

import ee.kmtster.compact_blocks.listeners.UncompactListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ee.kmtster.compact_blocks.StringUtils.snakeCase;

public final class CompactBlocks extends JavaPlugin {
    public final static Map<NamespacedKey, CompactBlock> compacts = new LinkedHashMap<>();
    private final static List<Material> occludingMaterials = Stream.of(Material.values()).filter(Material::isOccluding).collect(Collectors.toList());
    private final static List<Material> shulkerBoxes = Stream.of(Material.values()).filter(m -> m.name().endsWith("SHULKER_BOX")).collect(Collectors.toList());

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("Enabled CompactBlocks plugin.");

        new UncompactListener(this);

        List<Material> excludedMaterials = Arrays.asList(
                Material.ICE, Material.PACKED_ICE, Material.LOOM,
                Material.BARREL, Material.SMOKER, Material.BLAST_FURNACE,
                Material.CARTOGRAPHY_TABLE, Material.FLETCHING_TABLE, Material.SMITHING_TABLE,
                Material.BEE_NEST, Material.BEEHIVE, Material.INFESTED_STONE,
                Material.INFESTED_COBBLESTONE, Material.INFESTED_STONE_BRICKS, Material.INFESTED_MOSSY_STONE_BRICKS,
                Material.INFESTED_CHISELED_STONE_BRICKS, Material.INFESTED_CRACKED_STONE_BRICKS, Material.SPAWNER,
                Material.COMMAND_BLOCK, Material.BARRIER, Material.REPEATING_COMMAND_BLOCK,
                Material.CHAIN_COMMAND_BLOCK, Material.BLUE_ICE, Material.STRUCTURE_BLOCK,
                Material.JIGSAW
        );
        List<Material> base64Compacts = CompactBlockFromBase64.materials();
        occludingMaterials.removeAll(base64Compacts);
        occludingMaterials.removeAll(excludedMaterials);
        occludingMaterials.removeAll(shulkerBoxes);

        // blocks for which the skull associated with the player name represents the block well enough
        for (Material material : occludingMaterials) {
            CompactBlockRecipe newRecipe = new CompactBlockRecipe(createCompactRecipeNamespacedKey(material), new CompactBlockFromName(material));

            NamespacedKey inputKey = createCompactItemNamespacedKey(material);
            compacts.put(inputKey, newRecipe.getCompactBlock());

            Bukkit.addRecipe(newRecipe.getShapedRecipe());
        }

        // blocks for which a base64 string is used to generate the skull
        for (Material material : base64Compacts) {
            CompactBlockRecipe newRecipe = new CompactBlockRecipe(createCompactRecipeNamespacedKey(material), new CompactBlockFromBase64(material));

            NamespacedKey inputKey = createCompactItemNamespacedKey(material);
            compacts.put(inputKey, newRecipe.getCompactBlock());

            Bukkit.addRecipe(newRecipe.getShapedRecipe());
        }
    }

    private NamespacedKey createCompactItemNamespacedKey(Material material) {
        return new NamespacedKey(this, "compact_" + snakeCase(material.name()));
    }

    private NamespacedKey createCompactRecipeNamespacedKey(Material material) {
        return new NamespacedKey(this, "compact_" + snakeCase(material.name()) + "_recipe");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("To disable CompactBlocks plugin remove the plugin jar.");
    }
}
