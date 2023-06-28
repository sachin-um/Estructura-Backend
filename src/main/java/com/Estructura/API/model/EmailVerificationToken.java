package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public EmailVerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime=this.getTokenExpirationTime();
    }

    public EmailVerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime=this.getTokenExpirationTime();
    }

    private Date getTokenExpirationTime() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,10);
        return new Date(calendar.getTime().getTime());
    }
}
