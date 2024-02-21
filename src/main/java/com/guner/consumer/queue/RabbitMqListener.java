package com.guner.consumer.queue;

import com.guner.consumer.entity.ChargingRecord;
import com.guner.consumer.service.ChargingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * https://docs.spring.io/spring-amqp/docs/current/reference/html/#receiving-batch
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqListener {

    private final ChargingRecordService chargingRecordService;


    //@RabbitListener(queues = "${single-consumer.queue.name.single-queue}", containerFactory = "rabbitListenerContainerFactory")
    @RabbitListener(queues = "${single-consumer.queue.name.single-queue}")
    public void listenSingle(ChargingRecord chargingRecord) {
        log.debug("Charging Message Received, thread: {}", Thread.currentThread().getName());
        chargingRecordService.createChargingRecord(chargingRecord);
    }
}
