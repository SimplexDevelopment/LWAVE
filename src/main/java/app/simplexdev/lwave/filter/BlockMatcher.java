package app.simplexdev.lwave.filter;

import app.simplexdev.lwave.block.BlockInfo;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface BlockMatcher
{
    Mono<BlockMatcher> TYPE = Mono.just((source, target) -> source.getType()
                                                                  .map(type -> type.equals(target.getType().block()))
                                                                  .blockOptional()
                                                                  .orElse(false));

    boolean query(@NotNull BlockInfo source, @NotNull BlockInfo target);

    default boolean match(@NotNull BlockInfo source, @NotNull BlockInfo target, BlockFilter... filters)
    {
        for (BlockFilter filter : filters)
        {
            if (!filter.filter(target))
            {
                return false;
            }
        }

        return true;
    }
}
