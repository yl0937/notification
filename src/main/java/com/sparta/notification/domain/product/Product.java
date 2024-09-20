package com.sparta.notification.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer restock;
    private Integer count;

    public void updateCount(Integer count) {
        this.count = count;
    }
    public void plusRestock() {
        restock++;
    }
    public void minusCount() {
        count--;
    }
}
