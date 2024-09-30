package spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity

@SuperBuilder

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubService extends BaseEntity {

    @ManyToMany(mappedBy = "subServices")
    private Set<Expert> experts = new HashSet<>();

    @ManyToOne
    private Service service;

    @Column(unique = true)
    @NotBlank(message = "SubService Name cannot be Blank")
    @Size(min = 3, max = 30, message = "SubService Name must be less than {max} characters" +
                                       "and greater Than {min} characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "SubService Name can only contain letters")
    private String name;

    private String description;
    private Double BasePrice;
}
