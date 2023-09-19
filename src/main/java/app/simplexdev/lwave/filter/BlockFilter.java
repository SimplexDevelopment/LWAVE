package app.simplexdev.lwave.filter;

import app.simplexdev.lwave.block.BlockInfo;

public interface BlockFilter
{
    boolean filter(BlockInfo target);
}
