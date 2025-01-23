import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {
    @Nested
    class Constructor {
        @Nested
        class Name {
            @Test
            public void nullNameException() {
                assertThrows(IllegalArgumentException.class, () -> new Horse(null, .0));
            }

            @Test
            public void nullNameMessage() {
                assertEquals("Name cannot be null.",
                        assertThrows(IllegalArgumentException.class, () -> new Horse(null, .0)).getMessage());
            }

            @ParameterizedTest
            @ValueSource(strings = {"", " ", "\t", "\n"})
            public void blankNameException(String name) {
                assertThrows(IllegalArgumentException.class, () -> new Horse(name, .0));
            }

            @ParameterizedTest
            @ValueSource(strings = {"", " ", "\t", "\n"})
            public void blankNameMessage(String name) {
                assertEquals("Name cannot be blank.",
                        assertThrows(IllegalArgumentException.class, () -> new Horse(name, .0)).getMessage());
            }
        }

        @Nested
        class Speed {
            @Test
            public void negativeSpeedException() {
                assertThrows(IllegalArgumentException.class, () -> new Horse("name", -.1));
            }

            @Test
            public void negativeSpeedMessage() {
                assertEquals("Speed cannot be negative.",
                        assertThrows(IllegalArgumentException.class, () -> new Horse("name", -.1)).getMessage());
            }
        }

        @Nested
        class Distance {
            @Test
            public void negativeDistanceException() {
                assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1.5, -.6));
            }

            @Test
            public void negativeDistanceMessage() {
                assertEquals("Distance cannot be negative.",
                        assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1.5, -.6)).getMessage());
            }
        }
    }

    @Nested
    class Methods {
        @Test
        public void getName() {
            Horse horse = new Horse("bae", 1.5);
            assertEquals("bae", horse.getName());
        }

        @Test
        public void getSpeed() {
            Horse horse = new Horse("bae", 1.5);
            assertEquals(1.5, horse.getSpeed());
        }

        @Test
        public void getDistance() {
            Horse horseDistance = new Horse("bae", 1.5, 0.9);
            Horse horseNoDistance = new Horse("bae", 1.5);

            assertEquals(0.9, horseDistance.getDistance());
            assertEquals(.0, horseNoDistance.getDistance());
        }

        @Test
        public void move() {
            try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
                Horse horse = new Horse("name", 1.0);
                horse.move();
                mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            }
        }

        @ParameterizedTest
        @CsvSource({"0.5, 10.0, 5.0",
                "0.3, 20.0, 4.0"})
        public void testRandomDouble(double mockValue, double distance, double speed) {
            try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
                mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(mockValue);
                Horse horse = new Horse("name", speed, distance);
                horse.move();
                assertEquals(distance + speed * mockValue, horse.getDistance());
            }
        }
    }
}
