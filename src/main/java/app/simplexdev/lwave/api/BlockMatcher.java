package app.simplexdev.lwave.api;

public interface BlockMatcher
{
    BlockMatcher TYPE = (compareFrom, compareTo) -> compareFrom.getBlockType()
                                                               .equalsIgnoreCase(compareTo.getBlockType());

    boolean query(final BlockInfo compareFrom, final BlockInfo compareTo);

    default boolean matches(final BlockInfo compareFrom, final BlockInfo compareTo)
    {
        return !compareTo.bukkitType().isAir() && query(compareFrom, compareTo);
    }
}
