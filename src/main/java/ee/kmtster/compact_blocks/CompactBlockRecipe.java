package ee.kmtster.compact_blocks;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class CompactBlockRecipe {

    private final CompactBlock compactBlock;
    private final ShapedRecipe shapedRecipe;

    public CompactBlockRecipe(NamespacedKey key, CompactBlock compactBlock) {
        this.compactBlock = compactBlock;

        this.shapedRecipe = new ShapedRecipe(key, compactBlock.getCompactItem());
        this.shapedRecipe.shape("xxx", "xxx", "xxx");
        this.shapedRecipe.setIngredient('x', compactBlock.getUncompactMaterial());
    }

    public ShapedRecipe getShapedRecipe() {
        return shapedRecipe;
    }

    public CompactBlock getCompactBlock() {
        return compactBlock;
    }

    @Override
    public String toString() {
        return "CompactRecipe{" +
                "compactBlock=" + compactBlock +
                ", shapedRecipe=" + shapedRecipe +
                '}';
    }
}
