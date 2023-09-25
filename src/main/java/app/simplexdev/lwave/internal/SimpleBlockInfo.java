package app.simplexdev.lwave.internal;

import app.simplexdev.lwave.api.BlockInfo;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class SimpleBlockInfo implements BlockInfo
{
    private final int x;
    private final int y;
    private final int z;
    private final UUID worldUID;
    private final String blockData;
    private final String blockType;

    public SimpleBlockInfo(final Block block)
    {
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.blockData = block.getBlockData().getAsString();
        this.blockType = block.getType().toString();
        this.worldUID = block.getWorld().getUID();
    }

    @Override
    public String getBlockData()
    {
        return this.blockData;
    }

    @Override
    public String getBlockType()
    {
        return this.blockType;
    }

    @Override
    public int getX()
    {
        return this.x;
    }

    @Override
    public int getY()
    {
        return this.y;
    }

    @Override
    public int getZ()
    {
        return this.z;
    }

    @Override
    public Block bukkit()
    {
        return toLocation().getBlock();
    }

    @Override
    public BlockState bukkitState()
    {
        return bukkit().getState();
    }

    @Override
    public boolean isMineable(final ItemStack tool)
    {
        return deserializeBlockData().isPreferredTool(tool);
    }

    @Override
    public Material bukkitType()
    {
        return Material.matchMaterial(this.blockType);
    }

    @Override
    public Location toLocation()
    {
        return new Location(bukkitWorld(), this.x, this.y, this.z);
    }

    @Override
    public BlockData deserializeBlockData()
    {
        return Bukkit.getServer().createBlockData(this.blockData);
    }

    @Override
    public World bukkitWorld()
    {
        return Bukkit.getServer().getWorld(this.worldUID);
    }
}
