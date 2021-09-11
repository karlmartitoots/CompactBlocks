package ee.kmtster.compact_blocks.listeners;

import ee.kmtster.compact_blocks.model.CompactBlock;
import ee.kmtster.compact_blocks.CompactBlocksPlugin;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import static ee.kmtster.compact_blocks.StringUtils.snakeCase;

public class UncompactListener implements Listener {

    private final CompactBlocksPlugin plugin;

    public UncompactListener(CompactBlocksPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!isRightClickAction(event.getAction())) return;

        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (!hasSkullMeta(itemInMainHand))
            return;

        String name = ChatColor.stripColor(itemInMainHand.getItemMeta().getDisplayName());
        if (!CompactBlock.isCompactBlockName(name))
            return;

        final NamespacedKey key;
        try {
            key = new NamespacedKey(this.plugin, snakeCase(name));
        } catch (Throwable ignore) { // creating namespaced key on a non-compact item might produce exceptions due to invalid characters
            return;
        }

        CompactBlock compactBlock = CompactBlocksPlugin.compacts.get(key);
        if (compactBlock == null) // is a compact item
            return;

        event.setCancelled(true);

        ItemStack uncompactItem = new ItemStack(compactBlock.getUncompactMaterial());

        Player p = event.getPlayer();

        uncompactItem.setAmount(p.isSneaking() ? itemInMainHand.getAmount() * 9 : 9);
        p.getInventory().removeItem(p.isSneaking() ? itemInMainHand : oneOf(itemInMainHand));
        p.getWorld().dropItem(p.getLocation(), uncompactItem);
    }

    private boolean isRightClickAction(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean hasSkullMeta(ItemStack itemStack) {
        return itemStack.getItemMeta() instanceof SkullMeta;
    }

    private ItemStack oneOf(ItemStack item) {
        ItemStack oneItem = new ItemStack(item);
        oneItem.setAmount(1);

        return oneItem;
    }
}
