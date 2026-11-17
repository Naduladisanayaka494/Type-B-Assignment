package digital.typeb.helloworld.service;

import digital.typeb.helloworld.dto.GreetingResponse;
import digital.typeb.helloworld.exception.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link GreetingService} covering valid inputs, invalid inputs, and edge cases.
 */
class GreetingServiceTest {

    private GreetingService greetingService;

    @BeforeEach
    void setUp() {
        greetingService = new GreetingService();
    }

    @Test
    @DisplayName("Should return 'Hello Alice' when input is lowercase 'alice'")
    void shouldReturnFormattedGreetingForLowercaseInput() {
        GreetingResponse response = greetingService.generateGreeting("alice");
        assertNotNull(response);
        assertEquals("Hello Alice", response.message());
    }

    @Test
    @DisplayName("Should return 'Hello Alice' when input is already capitalized 'Alice'")
    void shouldReturnFormattedGreetingForCapitalizedInput() {
        GreetingResponse response = greetingService.generateGreeting("Alice");
        assertEquals("Hello Alice", response.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a", "A", "alice", "Alice",
            "bob", "Bob",
            "charlie", "Charlie",
            "david", "David",
            "eve", "Eve",
            "frank", "Frank",
            "grace", "Grace",
            "heidi", "Heidi",
            "ivan", "Ivan",
            "judy", "Judy",
            "karl", "Karl",
            "laura", "Laura",
            "m", "M", "mallory", "Mallory"
    })
    @DisplayName("Should accept all names starting with first half of the alphabet (A-M or a-m)")
    void shouldAcceptNamesInFirstHalfOfAlphabet(String validName) {
        GreetingResponse response = greetingService.generateGreeting(validName);
        String expectedName = validName.substring(0, 1).toUpperCase() + validName.substring(1);
        assertEquals("Hello " + expectedName, response.message());
    }

    @Test
    @DisplayName("Should trim surrounding whitespace for valid names")
    void shouldTrimWhitespaceForValidName() {
        GreetingResponse response = greetingService.generateGreeting("  alice  ");
        assertEquals("Hello Alice", response.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "n", "N", "nathan", "Nathan",
            "oscar", "Oscar",
            "peggy", "Peggy",
            "quentin", "Quentin",
            "rupert", "Rupert",
            "sybil", "Sybil",
            "trent", "Trent",
            "ursula", "Ursula",
            "victor", "Victor",
            "walter", "Walter",
            "xavier", "Xavier",
            "yvonne", "Yvonne",
            "z", "Z", "zach", "Zach"
    })
    @DisplayName("Should throw InvalidNameException for names starting with second half of the alphabet (N-Z or n-z)")
    void shouldRejectNamesInSecondHalfOfAlphabet(String invalidName) {
        InvalidNameException exception = assertThrows(
                InvalidNameException.class,
                () -> greetingService.generateGreeting(invalidName)
        );
        assertEquals("Invalid Input", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @DisplayName("Should throw InvalidNameException when name is null, empty, or whitespace-only")
    void shouldRejectNullEmptyOrBlankNames(String blankName) {
        InvalidNameException exception = assertThrows(
                InvalidNameException.class,
                () -> greetingService.generateGreeting(blankName)
        );
        assertEquals("Invalid Input", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1alice", "@alice", "-bob", "_charlie", "!david", "Élodie", "Änne"})
    @DisplayName("Should throw InvalidNameException when name starts with non-English alphabet character")
    void shouldRejectNonEnglishAlphabetStartingCharacters(String nonAlphaName) {
        InvalidNameException exception = assertThrows(
                InvalidNameException.class,
                () -> greetingService.generateGreeting(nonAlphaName)
        );
        assertEquals("Invalid Input", exception.getMessage());
    }
}
