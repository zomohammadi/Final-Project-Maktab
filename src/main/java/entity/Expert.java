package entity;

import enumaration.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Expert extends Users {

    @Lob
    private byte[] picture;

    @ManyToMany
    private Set<SubWork> subWorks;

    @Builder.Default
    private Status status = Status.New;

    private int score;
}
