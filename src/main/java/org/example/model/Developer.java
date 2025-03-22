package org.example.model;

import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Developer {

    private Long id;
    private String firstName;
    private String lastName;
    private Status status;
    private String specialty;
    private List<String> skills;

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"firstName\": \"" + firstName + "\",\n" +
                "  \"lastName\": \"" + lastName + "\",\n" +
                "  \"status\": \"" + status + "\",\n" +
                "  \"specialty\": " + (specialty != null ? "\"" + specialty + "\"" : "null") + ",\n" +
                "  \"skills\": " + (skills != null ?
                skills.stream()
                        .collect(Collectors.joining(", ", "\"", "\"")) // Убраны лишние кавычки
                : "\"\"") + "\n" +
                "}";
    }
}
