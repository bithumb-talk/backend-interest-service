package com.bithumb.interest.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@Table(name="interest")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="interest_no")
    private long interest_id;

    @Column(name="interest_symbol")
    private String symbol;

    @Column(name="interest_korean")
    private String korean;

    @Column(name="interest_market")
    private String market;

    @Column(name="interest_userId")
    private long userId;

    @Builder
    public Interest(String symbol, String korean, String market, long userId ){
        this.symbol = symbol;
        this.korean = korean;
        this.market = market;
        this.userId = userId;
    }
}
