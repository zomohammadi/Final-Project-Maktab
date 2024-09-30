package spring.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity

@SuperBuilder

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends Users {

}
