package ee.kmtster.compact_blocks.model;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class CompactBlockRecipe {

    private final CompactBlock compactBlock;
    private final ShapedRecipe recipe;

    public CompactBlockRecipe(NamespacedKey key, CompactBlock compactBlock) {
        this.compactBlock = compactBlock;

        this.recipe = new ShapedRecipe(key, compactBlock.getCompactItem());
        this.recipe.shape("xxx", "xxx", "xxx");
        this.recipe.setIngredient('x', compactBlock.getUncompactMaterial());
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public CompactBlock getCompactBlock() {
        return compactBlock;
    }

    @Override
    public String toString() {
        return "CompactRecipe{" +
                "compactBlock=" + compactBlock +
                ", shapedRecipe=" + recipe +
                '}';
    }
}
