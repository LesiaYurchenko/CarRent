package com.gmail.lesiiayurchenko.EventTicketBooking.data.entity;

import lombok.*;
import javax.persistence.*;

    @Entity
    @Table(name="ticket")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public class Ticket {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne (fetch = FetchType.EAGER)
        @JoinColumn(name="event_id", referencedColumnName = "id", nullable=false)
        private Event event;
        @ManyToOne (fetch = FetchType.EAGER)
        @JoinColumn(name="customer_id", referencedColumnName = "id", nullable=false)
        private User user;
        @ManyToOne (fetch = FetchType.EAGER)
        @JoinColumn(name="category_id", referencedColumnName = "id", nullable=false)
        private Category category;
        @Column
        private int place;
    }

