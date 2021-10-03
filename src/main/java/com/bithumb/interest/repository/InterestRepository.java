package com.bithumb.interest.repository;


import com.bithumb.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    List<Interest> findByUserId(long userId);
    Boolean existsInterestBySymbolAndUserId(String symbol,long userId);
    void deleteInterestBySymbolAndUserId(String symbol, long userId);
}
