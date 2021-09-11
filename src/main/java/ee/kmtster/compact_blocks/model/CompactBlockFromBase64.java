package ee.kmtster.compact_blocks.model;

import ee.kmtster.compact_blocks.SkullCreator;
import org.bukkit.Material;

public class CompactBlockFromBase64 extends CompactBlock {
    public CompactBlockFromBase64(Material material, String base64) {
        super(material, SkullCreator.itemFromBase64(base64));
    }
}
