package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Work extends BaseEntity {

    @Column(unique = true)
    @NotBlank(message = "Work Name cannot be Blank")
    @Size(min = 3,max = 30, message = "Work Name must be less than {max} characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Work Name can only contain letters")
    private String name;
}
