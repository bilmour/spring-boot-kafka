package com.seilks.nodhos.kafka;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Map;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<GenericRecord, GenericRecord> kafkaAvroTemplate;


    public void sendMessage(String topicName,GenericRecord key, GenericRecord record, Map<String, String> headers) {

        ProducerRecord<GenericRecord, GenericRecord> pRecord = new ProducerRecord<>(topicName, key, record);

        // Producer Record Headers ---------
        if (headers != null) {
            headers.forEach((k,v)->pRecord.headers().add(k, v.getBytes()));
        }

        ListenableFuture<SendResult<GenericRecord, GenericRecord>> future = kafkaAvroTemplate.send(pRecord);

        future.addCallback(new ListenableFutureCallback<SendResult<GenericRecord, GenericRecord>>() {

            @Override
            public void onSuccess(SendResult<GenericRecord, GenericRecord> result) {
                logger.info("Sent message=[" + record + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message=[" + record + "] due to : " , ex.getMessage());
            }
        });
    }


}