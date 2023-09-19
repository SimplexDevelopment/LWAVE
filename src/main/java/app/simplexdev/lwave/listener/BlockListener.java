package app.simplexdev.lwave.listener;

import app.simplexdev.lwave.block.BlockInfo;
import app.simplexdev.lwave.filter.BlockMatcher;
import app.simplexdev.lwave.queue.BlockScanner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener
{
    @EventHandler
    public void breakBlock(BlockBreakEvent event)
    {
        final ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        final BlockInfo source = new BlockInfo(event.getBlock());
        final BlockScanner scanner = new BlockScanner();
        scanner.recursiveScan(64,
                              source,
                              BlockMatcher.TYPE.block(),
                              filter0 -> filter0.isPreferredTool(item),
                              filter1 -> !event.isCancelled())
               .doOnNext(d ->
                         {
                             while (!d.isEmpty())
                             {
                                 final BlockInfo block = d.poll();
                                 block.bukkit()
                                      .doOnNext(b -> b.breakNaturally(item))
                                      .block();
                             }
                         })
               .subscribe();
    }
}
