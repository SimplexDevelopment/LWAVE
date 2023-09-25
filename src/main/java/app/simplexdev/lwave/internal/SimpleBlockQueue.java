package app.simplexdev.lwave.internal;

import app.simplexdev.lwave.api.BlockInfo;
import app.simplexdev.lwave.api.BlockMatcher;
import app.simplexdev.lwave.api.BlockQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleBlockQueue implements BlockQueue
{

    @Override
    public Queue<BlockInfo> from(BlockInfo origin, BlockMatcher matcher)
    {
        final Queue<BlockInfo> queue = new ConcurrentLinkedQueue<>();
        int r = 1; // cube radius
        int cubeSize = 2 * r + 1; // cube size

        for (int i = 0; i < cubeSize * cubeSize * cubeSize; i++) {
            int x = (i / (cubeSize * cubeSize)) % cubeSize - r;
            int y = ((i / cubeSize) % cubeSize) - r;
            int z = (i % cubeSize) - r;

            int dx = origin.getX() + x;
            int dy = origin.getY() + y;
            int dz = origin.getZ() + z;

            BlockInfo block = new SimpleBlockInfo(origin.bukkitWorld().getBlockAt(dx, dy, dz));
            if (matcher.matches(origin, block)) {
                queue.offer(block);
            }
        }
        return queue;
    }
}
