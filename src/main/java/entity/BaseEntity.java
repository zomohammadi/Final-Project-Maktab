package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.ZonedDateTime;

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime createDate;

    private ZonedDateTime lastUpdateDate;

    @PrePersist
    public void perPersist() {
        setCreateDate(ZonedDateTime.now());
        setLastUpdateDate(ZonedDateTime.now());
    }

    @PreUpdate
    public void perUpdate() {
        setLastUpdateDate(ZonedDateTime.now());
    }

}
