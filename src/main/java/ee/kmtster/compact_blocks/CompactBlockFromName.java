package ee.kmtster.compact_blocks;

import org.bukkit.Material;

public class CompactBlockFromName extends CompactBlock {
    public CompactBlockFromName(Material material) {
        super(material, SkullCreator.itemFromName(material.name()));
    }
}
