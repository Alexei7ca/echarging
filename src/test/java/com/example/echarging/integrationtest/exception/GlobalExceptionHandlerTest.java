package com.example.echarging.integrationtest.exception;

import com.example.echarging.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
@Import(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].detail").value("Resource not found"))
                .andExpect(jsonPath("$.errors[0].status").value("404"));
    }

    @Test
    public void testHandleBadRequestExceptions() throws Exception {
        mockMvc.perform(get("/test/bad-request")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].detail").value("Bad request"))
                .andExpect(jsonPath("$.errors[0].status").value("400"));
    }

    @Test
    public void testHandleConflictException() throws Exception {
        mockMvc.perform(get("/test/conflict")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].detail").value("Conflict occurred"))
                .andExpect(jsonPath("$.errors[0].status").value("409"));
    }

    @Test
    public void testHandleUnauthorizedException() throws Exception {
        mockMvc.perform(get("/test/unauthorized")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors[0].detail").value("Unauthorized access"))
                .andExpect(jsonPath("$.errors[0].status").value("401"));
    }

    @Test
    public void testHandleForbiddenException() throws Exception {
        mockMvc.perform(get("/test/forbidden")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors[0].detail").value("Forbidden access"))
                .andExpect(jsonPath("$.errors[0].status").value("403"));
    }

    @Test
    public void testHandleServiceUnavailableException() throws Exception {
        mockMvc.perform(get("/test/service-unavailable")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.errors[0].detail").value("Service unavailable"))
                .andExpect(jsonPath("$.errors[0].status").value("503"));
    }
}