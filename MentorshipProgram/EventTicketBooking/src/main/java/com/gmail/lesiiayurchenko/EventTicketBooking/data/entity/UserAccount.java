package com.gmail.lesiiayurchenko.EventTicketBooking.data.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="customer_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccount {
    @Id
    @Column(name = "id")
    private Long id;
    private BigDecimal balance;

    public UserAccount(BigDecimal balance, User user) {
        this.balance = balance;
    }
}