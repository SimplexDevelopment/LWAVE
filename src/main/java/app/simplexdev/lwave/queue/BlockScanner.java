package app.simplexdev.lwave.queue;

import app.simplexdev.lwave.block.BlockInfo;
import app.simplexdev.lwave.filter.BlockFilter;
import app.simplexdev.lwave.filter.BlockMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public final class BlockScanner
{
    public Mono<BlockDeque> scan(final BlockInfo source, final BlockMatcher matcher, final BlockFilter... filters)
    {
        return Mono.create(sink ->
                           {
                               final BlockDeque deque = new BlockDeque();
                               final int sx = (int) source.getLocation().getX();
                               final int sy = (int) source.getLocation().getY();
                               final int sz = (int) source.getLocation().getZ();

                               for (int x = -1; x <= 1; x++)
                               {
                                   int dx = sx + x;
                                   for (int y = 0; y <= 1; y++)
                                   {
                                       int dy = sy + y;
                                       for (int z = -1; z <= 1; z++)
                                       {
                                           int dz = sz + z;
                                           final BlockInfo target = source.getLocation()
                                                                          .getWorld()
                                                                          .map(world -> world.getBlockAt(dx, dy, dz))
                                                                          .map(BlockInfo::new)
                                                                          .blockOptional()
                                                                          .orElseThrow(() -> new RuntimeException(
                                                                              "Failed to get block at " + dx + ", " + dy + ", " + dz));

                                           if (matcher.match(source, target, filters))
                                           {
                                               deque.offer(target);
                                           }
                                       }
                                   }
                               }

                               sink.success(deque);
                           });
    }

    public Flux<BlockDeque> recursiveScan(final int iterations,
                                          final BlockInfo source,
                                          final BlockMatcher matcher,
                                          final BlockFilter... filters)
    {
        return Flux.create(sink -> scan(source, matcher, filters)
            .doOnNext(d ->
                      {
                          while (!d.isEmpty())
                          {
                              sink.next(d);

                              if (iterations == 0) return;

                              final BlockInfo block = d.poll();
                              recursiveScan(iterations - 1, block, matcher, filters)
                                  .doOnNext(sink::next)
                                  .subscribe();
                          }

                          sink.complete();
                      }));
    }
}
