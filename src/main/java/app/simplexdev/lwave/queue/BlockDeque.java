package app.simplexdev.lwave.queue;

import app.simplexdev.lwave.block.BlockInfo;
import java.util.ArrayDeque;
import java.util.Deque;
import reactor.core.publisher.Flux;

public final class BlockDeque
{
    private final Deque<BlockInfo> deque;

    public BlockDeque()
    {
        this.deque = new ArrayDeque<>(Integer.MAX_VALUE);
    }

    public BlockDeque offer(final BlockInfo info)
    {
        this.deque.offer(info);
        return this;
    }

    public BlockInfo poll()
    {
        return this.deque.poll();
    }

    public BlockInfo peek()
    {
        return this.deque.peek();
    }

    public BlockDeque remove(final BlockInfo info)
    {
        this.deque.remove(info);
        return this;
    }

    public BlockInfo removeFirst()
    {
        return this.deque.removeFirst();
    }

    public BlockInfo removeLast()
    {
        return this.deque.removeLast();
    }

    public boolean isEmpty()
    {
        return this.deque.isEmpty();
    }

    public int size()
    {
        return this.deque.size();
    }

    public BlockDeque clear()
    {
        this.deque.clear();
        return this;
    }

    public BlockInfo[] toArray()
    {
        return this.deque.toArray(new BlockInfo[size() + 1]);
    }

    public Flux<BlockInfo> getDeque()
    {
        return Flux.fromArray(this.toArray());
    }
}
