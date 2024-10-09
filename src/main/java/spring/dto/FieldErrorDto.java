package spring.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record FieldErrorDto(
        String field,
        String message
) {
}
