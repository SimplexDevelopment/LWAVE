package app.simplexdev.lwave.react;

import app.simplexdev.lwave.api.BlockInfo;
import app.simplexdev.lwave.api.BlockMatcher;
import app.simplexdev.lwave.api.BlockQueue;
import app.simplexdev.lwave.internal.SimpleBlockQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import reactor.core.publisher.Flux;

public class FluxBlockQueue
{
    public static Flux<BlockInfo> walkBlocks(final BlockInfo origin) {
        return Flux.fromStream(recursion(origin, 32).stream());
    }

    private static Queue<BlockInfo> recursion(final BlockInfo p0, final int iterationLimit) {
        if (iterationLimit <= 0) {
            return new ConcurrentLinkedQueue<>();
        }

        final BlockQueue queue = new SimpleBlockQueue();
        final Queue<BlockInfo> ret = new ConcurrentLinkedQueue<>();
        final Queue<BlockInfo> rec = new ConcurrentLinkedQueue<>();
        final Queue<BlockInfo> q = queue.from(p0, BlockMatcher.TYPE);
        while (!q.isEmpty()) {
            rec.offer(q.poll());
        }

        while (!rec.isEmpty()) {
            ret.addAll(recursion(rec.poll(), iterationLimit - 1));
        }

        return ret;
    }
}
