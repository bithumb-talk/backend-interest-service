package com.bithumb.interest.application;

import com.bithumb.utils.domain.Coin;
import com.bithumb.utils.service.CoinServiceImpl;
import com.bithumb.interest.api.dto.InterestResponse;
import com.bithumb.interest.domain.Interest;
import com.bithumb.interest.repository.InterestRepository;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class InterestServiceImplTest {

    //@InjectMocks
    InterestServiceImpl interestService;

    @Mock
    private InterestRepository interestRepository;
    @Mock
    private CoinServiceImpl coinService;

    final Interest interest = Interest.builder()
            .userId(1)
            .symbol("BTC")
            .korean("비트코인")
            .market("BTC_KRW")
            .build();
    final List<Interest> interests = Arrays.asList(interest);

    @BeforeEach
    void setUp() {
        this.interestService = new InterestServiceImpl(interestRepository, coinService);
    }

    @Test
    @DisplayName("관심코인 조회")
    void getInterests() {
        //given
        given(interestRepository.findByUserId(1)).willReturn(interests);

        //when
        List<InterestResponse> result = interestService.getInterests(1);

        //dto
        List<InterestResponse> response = List.of(
                InterestResponse.of(interest,1)
        );
        //then
        assertThat(result,is(response));


    }

    @Test
    void createInterest() throws IOException {
        //dto
        InterestResponse createDto = InterestResponse.of(interest,1);
        Coin coin = Coin.builder()
                .symbol("BTC")
                .korean("비트코인")
                .market("BTC_KRW")
                .build();

        HashMap<String, Coin> dto = new HashMap<>();
        dto.put(coin.getSymbol(), coin);
        given(coinService.getCoins()).willReturn(dto);
        given(interestRepository.save(any())).willReturn(interest);

        //when
        InterestResponse result = interestService.createInterest(1, "BTC");

        //then
        assertThat(result,is(createDto));

    }

    @Test
    void deleteInterest() {
        given(interestRepository.existsInterestBySymbolAndUserId("BTC", 1)).willReturn(true);
        interestService.deleteInterest("BTC",1);
        then(interestRepository).should(times(1)).deleteInterestBySymbolAndUserId("BTC",1);
    }
}