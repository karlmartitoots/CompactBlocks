package ee.kmtster.compact_blocks.model;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ee.kmtster.compact_blocks.StringUtils.capitalize;

public abstract class CompactBlock {
    private final static String COMPACT_ID = "Compact ";

    private final Material uncompactMaterial;
    private final ItemStack compactItem;

    public CompactBlock(Material material, ItemStack item) {
        this.uncompactMaterial = material;
        this.compactItem = item;
        this.compactItem.setAmount(1);
        this.compactItem.setItemMeta(meta());
    }

    public Material getUncompactMaterial() {
        return uncompactMaterial;
    }

    public ItemStack getCompactItem() {
        return compactItem;
    }

    protected String displayName() {
        return String.format("%s%s%s",
                ChatColor.GREEN,
                COMPACT_ID,
                capitalize(uncompactMaterial.name().replace("_", " ").toLowerCase()));
    }

    protected List<String> lore() {
        return Arrays.asList(
                ChatColor.YELLOW + "RIGHT-CLICK" + ChatColor.WHITE + " to uncompact",
                ChatColor.YELLOW + "SHIFT + RIGHT-CLICK " + ChatColor.WHITE + "to uncompact all"
        );
    }

    protected ItemMeta meta() {
        ItemMeta meta = this.compactItem.getItemMeta();

        Objects.requireNonNull(meta);

        meta.setDisplayName(displayName());
        meta.setLore(lore());

        return meta;
    }

    public static boolean isCompactBlockName(String itemName) {
        return itemName.toLowerCase().startsWith(COMPACT_ID.toLowerCase());
    }

    @Override
    public String toString() {
        return "CompactBlock{" +
                "uncompactMaterial=" + uncompactMaterial +
                ", compactItem=" + compactItem +
                '}';
    }
}
