package app.simplexdev.lwave.block;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import reactor.core.publisher.Mono;

public final class BlockLocation
{
    private final double x;
    private final double y;
    private final double z;
    private final UUID world;

    public BlockLocation(final double x, final double y, final double z, final World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world.getUID();
    }

    public BlockLocation(final Location location)
    {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.world = (location.getWorld() != null) ? location.getWorld().getUID() : null;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }

    public Mono<World> getWorld()
    {
        return Mono.justOrEmpty(Bukkit.getWorld(this.world));
    }

    public Mono<Block> getBlock()
    {
        return this.bukkit()
                   .map(Location::getBlock);
    }

    public BlockInfo toBlockInfo()
    {
        return this.getBlock()
                   .map(BlockInfo::new)
                   .block();
    }

    public Mono<Location> bukkit()
    {
        return this.getWorld()
                   .map(p0 -> new Location(p0, this.x, this.y, this.z));
    }
}
