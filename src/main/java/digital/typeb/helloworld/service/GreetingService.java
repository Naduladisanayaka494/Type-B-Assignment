package digital.typeb.helloworld.service;

import digital.typeb.helloworld.dto.GreetingResponse;
import digital.typeb.helloworld.exception.InvalidNameException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Service responsible for validating the input name and constructing the greeting response.
 */
@Service
public class GreetingService {

    /**
     * Generates a greeting if the provided name starts with a letter in the first half
     * of the English alphabet (A-M or a-m).
     *
     * @param name the raw name query parameter
     * @return a {@link GreetingResponse} containing the capitalized greeting
     * @throws InvalidNameException if the name is null, blank, starts with N-Z/n-z,
     *                              or starts with a non-English alphabet character
     */
    public GreetingResponse generateGreeting(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidNameException();
        }

        String trimmedName = name.trim();
        char firstChar = trimmedName.charAt(0);

        if (!isFirstHalfOfAlphabet(firstChar)) {
            throw new InvalidNameException();
        }

        String formattedName = formatName(trimmedName);
        return new GreetingResponse("Hello " + formattedName);
    }

    private boolean isFirstHalfOfAlphabet(char ch) {
        return (ch >= 'A' && ch <= 'M') || (ch >= 'a' && ch <= 'm');
    }

    private String formatName(String trimmedName) {
        String firstLetterUpper = trimmedName.substring(0, 1).toUpperCase(Locale.ROOT);
        if (trimmedName.length() == 1) {
            return firstLetterUpper;
        }
        return firstLetterUpper + trimmedName.substring(1);
    }
}
