package com.bithumb.interest.api;

import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import com.bithumb.interest.api.dto.InterestRequest;
import com.bithumb.interest.api.dto.InterestResponse;
import com.bithumb.interest.application.InterestServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//spring cloud에서 cors 설정
//@CrossOrigin(origins = "*", allowCredentials = "false")
@RequestMapping
@RequiredArgsConstructor
@RestController
@Api
@Slf4j
public class InterestController {
    private final InterestServiceImpl interestService;
    private static final Logger LOGGER = LoggerFactory.getLogger(InterestController.class);
    @GetMapping("/interests/{user-id}")
    public ResponseEntity<?> getInterests(@PathVariable(value = "user-id") long userId){
        LOGGER.info("{}.의 관심코인 리스트 ",userId);
        List<InterestResponse> interests = interestService.getInterests(userId);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.INTEREST_FINDALL_SUCCESS.getMessage(),interests);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/interest/{user-id}")
    public ResponseEntity<?> createInterest(@PathVariable(value = "user-id") long userId, @RequestBody InterestRequest interestRequest) throws IOException {
        String symbol = interestRequest.getSymbol();
        InterestResponse interestResponse =interestService.createInterest(userId,symbol);
        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                SuccessCode.INTEREST_CREATE_SUCCESS.getMessage(),interestResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/interest/{user-id}")
    public ResponseEntity<?> deleteInterest(@PathVariable(value = "user-id") long userId, @RequestBody InterestRequest interestRequest) {
        String symbol = interestRequest.getSymbol();
        interestService.deleteInterest(symbol,userId);
        ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
                SuccessCode.INTEREST_DELETE_SUCCESS.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
