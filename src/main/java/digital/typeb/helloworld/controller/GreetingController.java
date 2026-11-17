package digital.typeb.helloworld.controller;

import digital.typeb.helloworld.dto.ErrorResponse;
import digital.typeb.helloworld.dto.GreetingResponse;
import digital.typeb.helloworld.service.GreetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing the /hello-world endpoint.
 */
@RestController
@Tag(name = "Greeting API", description = "Endpoint for greeting users whose names start with A-M")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    /**
     * Handles GET /hello-world requests.
     *
     * @param name the optional query parameter specifying the person's name
     * @return HTTP 200 OK with {@link GreetingResponse} if valid, or HTTP 400 Bad Request via GlobalExceptionHandler
     */
    @Operation(
            summary = "Generate a greeting message",
            description = "Returns 'Hello {Name}' if the first letter of the name is in the first half of the English alphabet (A-M or a-m). Returns 400 Bad Request otherwise."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Valid name (starts with A-M or a-m)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GreetingResponse.class),
                            examples = @ExampleObject(value = "{\"message\": \"Hello Alice\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input (starts with N-Z, n-z, missing, or empty)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Invalid Input\"}")
                    )
            )
    })
    @GetMapping(value = "/hello-world", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GreetingResponse> sayHello(
            @Parameter(description = "Name of the person to greet (e.g., alice)", example = "alice")
            @RequestParam(name = "name", required = false) String name) {
        GreetingResponse response = greetingService.generateGreeting(name);
        return ResponseEntity.ok(response);
    }
}
