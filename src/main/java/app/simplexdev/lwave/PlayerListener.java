package app.simplexdev.lwave;

import app.simplexdev.lwave.api.BlockInfo;
import app.simplexdev.lwave.internal.SimpleBlockInfo;
import app.simplexdev.lwave.react.FluxBlockQueue;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener
{
    public void blockBreak(final BlockBreakEvent event)
    {
        final BlockInfo block = new SimpleBlockInfo(event.getBlock());
        FluxBlockQueue.walkBlocks(block)
                      .filter(b -> b.isMineable(event.getPlayer()
                                                     .getInventory()
                                                     .getItemInMainHand()))
                      .doOnNext(b ->
                                {
                                    b.bukkit()
                                     .breakNaturally(event.getPlayer()
                                                          .getInventory()
                                                          .getItemInMainHand());
                                    durabilityMod(event.getPlayer()
                                                       .getInventory()
                                                       .getItemInMainHand());
                                })
                      .doOnComplete(() -> event.getPlayer()
                                               .sendMessage("Successfully Vein-Mined!"))
                      .subscribe();
    }

    private void durabilityMod(final ItemStack item)
    {
        final ItemMeta meta = item.getItemMeta();

        if (meta instanceof Damageable d)
        {
            d.setDamage(d.getDamage() + 1);
        }

        item.setItemMeta(meta);
    }
}
