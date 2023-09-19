package app.simplexdev.lwave.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import reactor.core.publisher.Mono;

public final class BlockInfo
{
    private final BlockLocation location;
    private final Material type;

    public BlockInfo(final Block block)
    {
        this.location = new BlockLocation(block.getLocation());
        this.type = block.getType();
    }

    public BlockLocation getLocation()
    {
        return this.location;
    }

    public Mono<Material> getType()
    {
        return Mono.just(this.type);
    }

    public Mono<Block> bukkit()
    {
        return this.getLocation()
                   .getBlock();
    }

    public boolean isPreferredTool(final ItemStack stack)
    {
        return this.bukkit()
                   .map(b -> b.isPreferredTool(stack))
                   .blockOptional()
                   .orElse(false);
    }

}
