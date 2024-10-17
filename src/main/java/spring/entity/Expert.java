package spring.entity;

import jakarta.persistence.*;
import spring.enumaration.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
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
    private Set<SubService> subServices = new HashSet<>();

    @Builder.Default
    private Status status = Status.NEW;

    private double score;

    private int performanceScore;
}
