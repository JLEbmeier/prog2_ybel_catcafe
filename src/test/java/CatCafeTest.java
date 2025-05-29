import catcafe.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import tree.Empty;
import tree.Node;
import tree.TreeVisitor;

/**
 * JUnit 5 tests for the CatCafe class.
 */
public class CatCafeTest {
    private CatCafe cafe;
    private FelineOverLord sooky, esmeralda, morticia, fitzby, tapsy;

    @BeforeEach
    void setUp() {
        cafe = new CatCafe();
        sooky = new FelineOverLord("Miss Chief Sooky", 2);
        esmeralda = new FelineOverLord("Gwenapurr Esmeralda", 3);
        morticia = new FelineOverLord("Morticia", 3);
        fitzby = new FelineOverLord("Fitzby Darnsworth", 5);
        tapsy = new FelineOverLord("Little Tapsy", 1);
    }

    /**
     * Tests adding a cat and retrieving it by name.
     */
    @Test
    void testAddCatAndGetByNameFound() {
        cafe.addCat(sooky);
        Optional<FelineOverLord> result = cafe.getCatByName("Miss Chief Sooky");
        assertTrue(result.isPresent());
        assertEquals(sooky, result.get());
    }

    /**
     * Tests searching for a non-existent cat by name.
     */
    @Test
    void testGetByNameNotFound() {
        cafe.addCat(sooky);
        assertFalse(cafe.getCatByName("Fluffy").isPresent());
    }

    /**
     * Tests searching with a null name.
     */
    @Test
    void testGetByNameNull() {
        assertFalse(cafe.getCatByName(null).isPresent());
    }

    /**
     * Tests finding a cat by weight range.
     */
    @Test
    void testGetByWeightFound() {
        cafe.addCat(esmeralda);
        Optional<FelineOverLord> result = cafe.getCatByWeight(3, 4);
        assertTrue(result.isPresent());
        assertEquals(esmeralda, result.get());
    }

    /**
     * Tests searching for a cat in a weight range with no match.
     */
    @Test
    void testGetByWeightNotFound() {
        cafe.addCat(sooky);
        assertFalse(cafe.getCatByWeight(6, 7).isPresent());
    }

    /**
     * Tests invalid weight ranges (negative or max < min).
     */
    @Test
    void testGetByWeightInvalidRange() {
        assertFalse(cafe.getCatByWeight(-1, 5).isPresent());
        assertFalse(cafe.getCatByWeight(5, 3).isPresent());
    }

    /**
     * Tests an empty café's behavior.
     */
    @Test
    void testEmptyCafe() {
        assertFalse(cafe.getCatByName("Morticia").isPresent());
        assertFalse(cafe.getCatByWeight(3, 4).isPresent());
        assertEquals(0, cafe.getCatCount());
    }

    /**
     * Tests adding multiple cats and verifying count and retrieval.
     */
    @Test
    void testMultipleCats() {
        cafe.addCat(tapsy);
        cafe.addCat(sooky);
        cafe.addCat(esmeralda);
        assertEquals(3, cafe.getCatCount());
        assertTrue(cafe.getCatByName("Gwenapurr Esmeralda").isPresent());
        assertTrue(cafe.getCatByWeight(2, 3).isPresent());
    }

    /**
     * Tests adding cats with the same weight.
     */
    @Test
    void testAddCatsWithSameWeight() {
        cafe.addCat(esmeralda); // Gewicht 3
        cafe.addCat(morticia); // Gewicht 3
        assertEquals(2, cafe.getCatCount());
        assertTrue(cafe.getCatByName("Gwenapurr Esmeralda").isPresent());
        assertTrue(cafe.getCatByName("Morticia").isPresent());
    }

    /**
     * Tests Inorder traversal order (weight, then name).
     */
    @Test
    void testInorderTraversalByWeightAndName() {
        cafe.addCat(tapsy); // Gewicht 1
        cafe.addCat(sooky); // Gewicht 2
        cafe.addCat(esmeralda); // Gewicht 3
        cafe.addCat(morticia); // Gewicht 3
        cafe.addCat(fitzby); // Gewicht 5

        // Inline InOrderVisitor für Test
        TreeVisitor<FelineOverLord> visitor = new TreeVisitor<>() {
            @Override
            public String visit(Empty<FelineOverLord> empty) {
                return "";
            }

            @Override
            public String visit(Node<FelineOverLord> node) {
                String left = node.leftChild().accept(this);
                String current = node.data().name();
                String right = node.rightChild().accept(this);
                return left + (left.isEmpty() ? "" : ", ") + current + (right.isEmpty() ? "" : ", ") + right;
            }
        };

        String result = cafe.accept(visitor);
        assertEquals("Little Tapsy, Miss Chief Sooky, Gwenapurr Esmeralda, Morticia, Fitzby Darnsworth", result);
    }

    /**
     * Tests cat count after adding multiple cats.
     */
    @Test
    void testGetCatCountAfterMultipleAdds() {
        cafe.addCat(tapsy);
        cafe.addCat(sooky);
        cafe.addCat(esmeralda);
        cafe.addCat(morticia);
        cafe.addCat(fitzby);
        assertEquals(5, cafe.getCatCount());
    }
}
