package digital.typeb.helloworld.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link GreetingController} verifying HTTP status codes and JSON payloads.
 */
@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /hello-world?name=alice -> 200 OK with {\"message\": \"Hello Alice\"}")
    void shouldReturn200AndHelloAliceForLowercaseAlice() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Aaron", "aaron", "Mary", "mary", "m", "A"})
    @DisplayName("GET /hello-world with boundary A-M names -> 200 OK")
    void shouldReturn200ForBoundaryFirstHalfNames(String name) throws Exception {
        String expectedName = name.substring(0, 1).toUpperCase() + name.substring(1);
        mockMvc.perform(get("/hello-world").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Hello " + expectedName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"nathan", "Nathan", "zach", "Zach", "n", "N", "z", "Z"})
    @DisplayName("GET /hello-world with N-Z names -> 400 Bad Request with {\"error\": \"Invalid Input\"}")
    void shouldReturn400ForSecondHalfNames(String name) throws Exception {
        mockMvc.perform(get("/hello-world").param("name", name))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("GET /hello-world without name parameter -> 400 Bad Request with {\"error\": \"Invalid Input\"}")
    void shouldReturn400WhenNameParameterIsMissing() throws Exception {
        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("GET /hello-world with empty or blank name parameter -> 400 Bad Request with {\"error\": \"Invalid Input\"}")
    void shouldReturn400WhenNameParameterIsEmptyOrBlank(String emptyOrBlank) throws Exception {
        mockMvc.perform(get("/hello-world").param("name", emptyOrBlank))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "@alice", "!bob"})
    @DisplayName("GET /hello-world with non-alphabetic first character -> 400 Bad Request with {\"error\": \"Invalid Input\"}")
    void shouldReturn400WhenNameStartsWithNonAlphabeticCharacter(String invalidName) throws Exception {
        mockMvc.perform(get("/hello-world").param("name", invalidName))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }
}
