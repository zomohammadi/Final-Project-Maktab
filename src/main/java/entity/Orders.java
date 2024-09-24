package entity;

import enumaration.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity

@SuperBuilder

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders extends BaseEntity {

    @ManyToOne
    private SubService subService;

    @ManyToOne
    private Customer customer;

    private Double priceSuggested;
    private String address;
    private OrderStatus orderStatus;
    private ZonedDateTime timeForServiceDone;
    private String serviceDescription;

}
