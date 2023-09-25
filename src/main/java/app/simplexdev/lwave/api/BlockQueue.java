package app.simplexdev.lwave.api;

import java.util.Queue;

/**
 * Represents a 3x3 cube surrounding an origin block.
 */
public interface BlockQueue
{
    Queue<BlockInfo> from(final BlockInfo origin, final BlockMatcher matcher);
}
