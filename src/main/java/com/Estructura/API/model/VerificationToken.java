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
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    private TokenType tokenType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(String token, User user, TokenType tokenType) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime=this.getTokenExpirationTime();
        this.tokenType=tokenType;
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime=this.getTokenExpirationTime();
    }

    public Date getTokenExpirationTime() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,1);
        return new Date(calendar.getTime().getTime());
    }
}
