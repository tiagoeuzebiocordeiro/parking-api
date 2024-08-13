package com.tiagocordeiro.parkingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "customer_has_spot")
@EntityListeners(AuditingEntityListener.class)
public class CustomerSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_number", nullable = false, unique = true, length = 15)
    private String receipt;

    @Column(name = "license_plate", nullable = false, length = 8)
    private String licensePlate;

    @Column(name = "brand", nullable = false, length = 45)
    private String brand;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @Column(name = "color", nullable = false, length = 45)
    private String color;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "exit_date")
    private LocalDateTime exitDate;

    @Column(name = "value", columnDefinition = "decimal(7,2)")
    private BigDecimal value;

    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "spot_id", nullable = false)
    private Spot spot;

    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerSpot that = (CustomerSpot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
