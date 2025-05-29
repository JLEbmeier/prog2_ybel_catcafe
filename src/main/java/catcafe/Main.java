package catcafe;

/** Starter for the cat-café task. */
public class Main {
    /**
     * And go.
     *
     * @param args command line parameters, not used
     */
    public static void main(String... args) {
        CatCafe cafe = new CatCafe();

        cafe.addCat(new FelineOverLord("Miss Chief Sooky", 2));
        cafe.addCat(new FelineOverLord("Gwenapurr Esmeralda", 3));
        cafe.addCat(new FelineOverLord("Morticia", 3));
        cafe.addCat(new FelineOverLord("Fitzby Darnsworth", 5));
        cafe.addCat(new FelineOverLord("DR Doom",20));

        System.out.println("Es schnurren " + cafe.getCatCount() + " Samtpfötchen.");

        cafe.getCatByWeight(3, 4)
            .ifPresentOrElse(
                cat -> System.out.println("Gewicht [3,4]: " + cat),
                () -> System.out.println("Keine Katze mit Gewicht im Bereich [3,4) gefunden")
            );

        cafe.getCatByName("Morticia")
            .ifPresentOrElse(
                cat -> System.out.println("Name 'Morticia': " + cat),
                () -> System.out.println("Keine Katze namens Morticia gefunden")
            );

        cafe.getCatByName("Miss Chief Sooky")
            .ifPresentOrElse(
                cat -> System.out.println("Name 'Miss Chief Sooky': " + cat),
                () -> System.out.println("Keine Katze namens Miss Chief Sooky gefunden")
            );
    }
}
