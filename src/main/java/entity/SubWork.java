package entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SubWork extends BaseEntity {
    @ManyToOne
    @JoinColumn//?
    private Expert expert;
}
