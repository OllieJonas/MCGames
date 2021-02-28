package me.ollie.games.util;

import com.google.common.util.concurrent.AtomicDouble;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A set that has additional features to get a random item from the set, based on pre-determined weightings.
 * Note that although remove operation exists, it has O(n) complexity, and therefore it is not advised to remove stuff
 * from this.
 * @param <E>
 */
public class ConcurrentWeightedRandomSet<E> extends AbstractSet<E> {

    private static final Double DEFAULT_WEIGHTING = 1.0D;

    private static final Random RANDOM = new Random();

    private final AtomicDouble totalWeight = new AtomicDouble(0.0D);

    private final Set<E> set;

    private final List<SetItem<E>> weightings;

    public ConcurrentWeightedRandomSet() {
        this.set = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.weightings = new CopyOnWriteArrayList<>();
    }

    @Override
    public boolean add(E elem) {
        return add(elem, DEFAULT_WEIGHTING);
    }

    public boolean add(E elem, double weighting) {
        set.add(elem);
        weightings.add(new SetItem<>(elem, weighting));

        totalWeight.getAndAdd(weighting);
        return true;
    }

    public E getRandom() {
        return getRandom(RANDOM);
    }

    public E getRandom(Random random) {
        if (set.isEmpty())
            throw new IllegalStateException("Set can't be empty!");

        double total = totalWeight.doubleValue();

        double r = random.nextDouble() * total;

        for (SetItem<E> weighting : weightings) {
            r -= weighting.weighting;
            if (r <= 0.0) return weighting.elem;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object elem) {
        E elemCast = (E) elem;
        set.remove(elemCast);
        weightings.removeIf(item -> item.elem == elem);
        return true;
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        return set.iterator();
    }

    @Override
    public int size() {
        return set.size();
    }

    private class SetItem<E> {
        E elem;
        double weighting;

        public SetItem(E elem, double weighting) {
            this.elem = elem;
            this.weighting = weighting;
        }
    }
}
