package com.bithumb.utils.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.bithumb.utils.domain.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;


@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {
    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;
    @Value("${cloud.aws.credentials.accessKey}")
    private String ACCESS_KEY;
    @Value("${cloud.aws.credentials.secretKey}")
    private String SECRET_KEY;
    @Value("${cloud.aws.s3.key}")
    private String KEY_NAME;

    public HashMap<String, Coin> getCoins() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
        S3Object o = s3.getObject(BUCKET_NAME,KEY_NAME);
        S3ObjectInputStream s3is = o.getObjectContent();
        Coin[] coins = objectMapper.readValue(s3is, Coin[].class);
        HashMap<String, Coin> map = new HashMap<String, Coin>();
        int i;
        for (i=0;i<coins.length;i++) {
            map.put(coins[i].getSymbol(),coins[i]);
        }
        return map;
    }
}