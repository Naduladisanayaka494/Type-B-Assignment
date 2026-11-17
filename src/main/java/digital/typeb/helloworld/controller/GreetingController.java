package digital.typeb.helloworld.controller;

import digital.typeb.helloworld.dto.GreetingResponse;
import digital.typeb.helloworld.service.GreetingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing the /hello-world endpoint.
 */
@RestController
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
    @GetMapping(value = "/hello-world", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GreetingResponse> sayHello(
            @RequestParam(name = "name", required = false) String name) {
        GreetingResponse response = greetingService.generateGreeting(name);
        return ResponseEntity.ok(response);
    }
}
