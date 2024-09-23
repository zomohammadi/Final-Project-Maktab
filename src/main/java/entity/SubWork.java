package entity;

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

import java.util.List;

@Entity

@SuperBuilder

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubWork extends BaseEntity {

    @ManyToMany(mappedBy = "subWorks")
    private List<Expert> experts;

    @ManyToOne
    private Work work;

    @Column(unique = true)
    @NotBlank(message = "SubWork Name cannot be Blank")
    @Size(min = 3, max = 30, message = "SubWork Name must be less than {max} characters" +
                                       "and greater Than {min} characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "SubWork Name can only contain letters")
    private String name;

    private String description;
    private Double BasePrice;
}
