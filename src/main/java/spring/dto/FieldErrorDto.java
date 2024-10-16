package spring.dto;

import lombok.Builder;

@Builder
public record FieldErrorDto(
        String field,
        String message
) {
}
