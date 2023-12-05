package com.example.gradesubmission.filters;

import java.io.IOException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JsonValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Check if the request method is POST
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            CustomHttpServletRequestWrapper wrappedRequest = new CustomHttpServletRequestWrapper(
                    (HttpServletRequest) request);

            String jsonData = wrappedRequest.getBody();
            System.out.println("JSON DATA: " + jsonData);
            JSONObject jsonSchema = new JSONObject(
                    new JSONTokener(JsonValidationFilter.class.getResourceAsStream("/emim-latest.json")));

            JSONObject jsonSubject = new JSONObject(jsonData);

            Schema schema = SchemaLoader.load(jsonSchema);
            try {
                schema.validate(jsonSubject);
                chain.doFilter(wrappedRequest, response);
            } catch (ValidationException e) {
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                JSONObject errorDetails = new JSONObject();
                errorDetails.put("error", "Validation failed");
                errorDetails.put("details", e.getMessage());
                httpResponse.getWriter().write(errorDetails.toString());
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}