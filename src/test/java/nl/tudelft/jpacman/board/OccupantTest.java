package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    @DisplayName("noStartSquare")
    void noStartSquare() {
        // Remove the following placeholder:
        assertThat(unit).isNotNull();
        assertThat(unit.hasSquare()).isFalse();

    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    @DisplayName("testOccupy")
    void testOccupy() {
        Square square = new BasicSquare();
        unit.occupy(square);
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit);
    }
    @Test
    @DisplayName("testReOccupy")
    void testReoccupy(){
        Square square = new BasicSquare();
        Square square1 = new BasicSquare();
        unit.occupy(square);
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit);
        unit.occupy(square1);
        assertThat(unit.getSquare()).isEqualTo(square1);
        assertThat(square1.getOccupants()).contains(unit);
    }

}
