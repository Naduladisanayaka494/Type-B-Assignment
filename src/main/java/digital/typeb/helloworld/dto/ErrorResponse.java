package digital.typeb.helloworld.dto;

/**
 * Response payload for invalid requests.
 *
 * @param error the error description (e.g., "Invalid Input")
 */
public record ErrorResponse(String error) {
}
