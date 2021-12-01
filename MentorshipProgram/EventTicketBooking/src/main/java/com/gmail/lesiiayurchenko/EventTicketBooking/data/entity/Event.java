package com.gmail.lesiiayurchenko.EventTicketBooking.data.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;

    public Event(String title, Date date) {
        this.title = title;
        this.date = date;
    }
}