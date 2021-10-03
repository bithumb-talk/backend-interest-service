package com.bithumb.coin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode @ToString
public class Coin {
    private String symbol;
    private String korean;
    private String market;
}
