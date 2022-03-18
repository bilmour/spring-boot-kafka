package com.seilks.nodhos.kafka;


import com.adeo.offer.cluster.events.cluster.model.v2.StoreClusterGroupValidated;
import com.adeo.offer.cluster.events.cluster.model.v2.StoreClusterGroupValidatedKey;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaListeners {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    @KafkaListener(topics = "${cluster_group.topic.validated}",
            clientIdPrefix = "nodhos-pil-cluster-group-validated",
            containerFactory = "kafkaListenerContainerFactory")
    public void clusterGroupValidatedListener(ConsumerRecord<StoreClusterGroupValidatedKey, StoreClusterGroupValidated> cr,
                                              @Payload Object payload) throws IOException {

        logger.info("Consumer Record  {}: ", cr);
        StoreClusterGroupValidated value = cr.value();

        // -- COnvertion avro vers custom



        logger.info("Consumer Record value {}: ", value.getString());

        logger.info("PayLoad class {} ", payload.getClass());
        logger.info("PayLoad content {} ", payload);

    }

}
