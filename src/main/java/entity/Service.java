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
public class Service extends BaseEntity {

    @Column(unique = true)
    @NotBlank(message = "Service Name cannot be Blank")
    @Size(min = 3,max = 30, message = "Service Name must be less than {max} characters"+
                                      "and greater Than {min} characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Service Name can only contain letters")
    private String name;
}
