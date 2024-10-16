package spring.entity;

import spring.enumaration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

//@MappedSuperclass

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@SuperBuilder

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"mobile_number", "role"}),
        @UniqueConstraint(columnNames = {"national_code", "role"})
})

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users extends BaseEntity {


    private String firstName;
    private String lastName;

    //@Column(unique = true)
    private String nationalCode;

    //@Column(unique = true)
    private String mobileNumber;

    @Builder.Default
    private ZonedDateTime registerDateAndTime = ZonedDateTime.now();

    @Column(unique = true)
    private String emailAddress;

    @Column(unique = true)
    String userName;

    @NotBlank(message = "Password cannot be Blank")
    String password;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    Credit credit;

    @Builder.Default
    private boolean isActive = true;
}
