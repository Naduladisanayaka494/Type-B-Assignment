package digital.typeb.helloworld.dto;

/**
 * Response payload for a valid greeting request.
 *
 * @param message the formatted greeting message (e.g., "Hello Alice")
 */
public record GreetingResponse(String message) {
}
