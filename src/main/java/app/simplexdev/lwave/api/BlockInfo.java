package app.simplexdev.lwave.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public interface BlockInfo
{
    String getBlockData();

    String getBlockType();

    int getX();

    int getY();

    int getZ();

    Block bukkit();

    BlockState bukkitState();

    boolean isMineable(ItemStack tool);

    Material bukkitType();

    Location toLocation();

    BlockData deserializeBlockData();

    World bukkitWorld();
}
