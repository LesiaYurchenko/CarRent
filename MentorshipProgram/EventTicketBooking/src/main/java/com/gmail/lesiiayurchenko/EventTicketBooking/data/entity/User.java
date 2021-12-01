package com.gmail.lesiiayurchenko.EventTicketBooking.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="customer", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserAccount userAccount;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
