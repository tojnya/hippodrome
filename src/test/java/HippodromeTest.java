import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Nested
    class Constructor {
        @Test
        public void nullHippodrome() {
            assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        }

        @Test
        public void nullHippodromeMessage() {
            assertEquals("Horses cannot be null.",
                    assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null)).getMessage());
        }

        @Test
        public void emptyHippodrome() {
            assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        }

        @Test
        public void emptyHippodromeMessage() {
            assertEquals("Horses cannot be empty.",
                    assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>())).getMessage());
        }
    }

    @Nested
    class Methods {
        @Test
        public void getHorses() {
            List<Horse> horses = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                horses.add(new Horse("horse" + i, i % 10 + 0.1));
            }
            Hippodrome hippodrome = new Hippodrome(horses);
            Assertions.assertArrayEquals(horses.toArray(), hippodrome.getHorses().toArray());
        }

        @Test
        public void move() {
            List<Horse> horses = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                Horse horseMock = mock(Horse.class);
                horses.add(horseMock);
            }
            Hippodrome hippodrome = new Hippodrome(horses);
            hippodrome.move();

            for (Horse horse : horses) {
                verify(horse).move();
            }
        }

        @Test
        public void getWinner() {
            List<Horse> horses = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                horses.add(new Horse("horse" + i, i % 10 + 0.1));
            }
            Hippodrome hippodrome = new Hippodrome(horses);
            double maxDistance = 0.0;
            String winnerName = "";

            hippodrome.move();
            for (Horse horse : horses) {
                if (horse.getDistance() > maxDistance) {
                    maxDistance = horse.getDistance();
                    winnerName = horse.getName();
                }
            }

            assertEquals(winnerName, hippodrome.getWinner().getName());
            assertEquals(maxDistance, hippodrome.getWinner().getDistance());
        }
    }
}
