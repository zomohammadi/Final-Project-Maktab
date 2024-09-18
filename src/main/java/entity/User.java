package entity;

import enumaration.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@MappedSuperclass
//@Inheritance(strategy = InheritanceType.JOINED)

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Builder.Default
    private ZonedDateTime registerDateAndTime = ZonedDateTime.now();

    private String emailAddress;
    String userName;
    String password;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

   /* @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    Profile profile;*/

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    Credit credit;
}
