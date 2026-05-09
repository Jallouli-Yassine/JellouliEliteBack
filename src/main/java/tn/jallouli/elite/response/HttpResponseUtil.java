package tn.jallouli.elite.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HttpResponseUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, int status, String error, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getServletPath());

        mapper.writeValue(response.getOutputStream(), body);
    }
}

