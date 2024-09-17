package entity;

import enumaration.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users extends BaseEntity {
    private String firstName;
    private String lastName;
   // private ZonedDateTime registerDateAndTime;
    private String emailAddress;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    Profile profile;

}
