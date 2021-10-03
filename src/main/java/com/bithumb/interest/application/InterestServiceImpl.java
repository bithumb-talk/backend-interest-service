package com.bithumb.interest.application;

import com.bithumb.coin.domain.Coin;
import com.bithumb.coin.service.CoinServiceImpl;
import com.bithumb.common.response.ErrorCode;
import com.bithumb.interest.api.dto.InterestResponse;
import com.bithumb.interest.domain.Interest;
import com.bithumb.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService{

    private final InterestRepository interestRepository;
    private final CoinServiceImpl coinService;

    @Override
    public List<InterestResponse> getInterests(long userId) {
        return InterestResponse.ofArray(interestRepository.findByUserId(userId),userId);
    }
    @Override
    public InterestResponse createInterest(long userId, String symbol) throws IOException {
        HashMap coins = coinService.getCoins();
        Coin coin = (Coin) coins.get(symbol);
        existsCoin(coins, symbol);
        if (existsDB(symbol, userId)) {
            throw new SecurityException(ErrorCode.ALREADY_EXISTS.getMessage());
        }
        Interest interest = Interest.builder()
                .userId(userId)
                .korean(coin.getKorean())
                .market(coin.getMarket())
                .symbol(symbol).build();
        return InterestResponse.of(interestRepository.save(interest),userId);
    }

    @Override
    public void deleteInterest(String symbol, long userId) {
        if (!existsDB(symbol,userId)){
            throw new SecurityException(ErrorCode.INTEREST_NOT_EXISTS.getMessage());
        };
        interestRepository.deleteInterestBySymbolAndUserId(symbol,userId);
    }



    private void existsCoin(HashMap coins, String symbol) throws IOException {
        Boolean exsitsCoin = coins.containsKey(symbol);
        if (!exsitsCoin) {
            throw new SecurityException(ErrorCode.COIN_NOT_EXISTS.getMessage());
        }
    }

    private Boolean existsDB(String symbol, long userId) {
        Boolean existsDB = interestRepository.existsInterestBySymbolAndUserId(symbol,userId).booleanValue();
        if (existsDB) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
