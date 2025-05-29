package catcafe;

/**
 * A purring feline overlord representing a cat in the cat café.
 *
 * Implements {@link Comparable} to sort cats in a binary search tree by weight, using
 * name as a tiebreaker for equal weights.
 *
 *
 * Change: Originally, {@link #compareTo(FelineOverLord)} used only weight
 * ({@code weight - o.weight}), treating cats with equal weights as duplicates, which
 * prevented insertion (e.g., "Morticia" was skipped). Now, it compares names when weights
 * are equal, ensuring all cats are inserted.
 *
 *
 * @param name   name of the cat
 * @param weight weight of the cat
 */
public record FelineOverLord(String name, int weight) implements Comparable<FelineOverLord> {
    @Override
    public int compareTo(FelineOverLord o) {
        int weightCompare = Integer.compare(weight, o.weight);
        if (weightCompare != 0) {
            return weightCompare;
        }
        return name.compareTo(o.name);
    }
}
