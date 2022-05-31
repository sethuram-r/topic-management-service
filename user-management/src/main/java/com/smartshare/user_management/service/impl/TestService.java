package com.smartshare.user_management.service.impl;

import com.smartshare.user_management.service.ITestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService implements ITestService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @Override
    public void test() {
        var sendResultListenableFuture = kafkaTemplate.send( "user", 1, "test value" );

        sendResultListenableFuture.addCallback( new KafkaSendCallback<>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info( "the data has been sent successfully at " + result.getProducerRecord().timestamp() );
            }

            @Override
            public void onFailure(KafkaProducerException e) {
                log.error( "Failure while sending the record " + e.getCause() );
            }
        } );
    }

    @KafkaListener(topics = "user", groupId = "user-consumer-group", clientIdPrefix = "consumer")
    public void listenGroupUser(String message) {
        log.info( "Received Message in group user: " + message );
    }
}