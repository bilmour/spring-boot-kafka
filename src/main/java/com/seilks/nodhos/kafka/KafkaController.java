package com.seilks.nodhos.kafka;

import com.adeo.offer.cluster.events.cluster.model.v2.ClusterValidated;
import com.adeo.offer.cluster.events.cluster.model.v2.StoreClusterGroupValidated;
import com.adeo.offer.cluster.events.cluster.model.v2.StoreClusterGroupValidatedKey;
import io.swagger.annotations.Api;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = {"Kafka"}, description = "Ressources pour produire des données vers KAfka")
@RequestMapping(value = "/kafka")
public class KafkaController {

    @Value(value = "${cluster_group.topic.validated}")
    private String topic;

    private final KafkaProducer producer;

    public KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }


    @PostMapping(value = "/ping")
    public String ping() {
        return "running";
    }


    @PostMapping(value = "/publish-message")
    public void publishMessage() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String sDate1 = "01/03/2020";
        LocalDate date;
        date = LocalDate.parse(sDate1, formatter);

        try {

            StoreClusterGroupValidatedKey key = StoreClusterGroupValidatedKey.newBuilder().setStoreClusterGroupIdentifier(12345).build();

            ClusterValidated cluster = ClusterValidated.newBuilder()
                    .setStoreClusterIdentifier(123456)
                    .setStoreClusterName("ABI")
                    .setStoreClusterDescription("ABI Description")
                    .setStoreClusterCreationCollaboratorIdentifier("ABI")
                    .setStoreClusterValidationCollaboratorIdentifier("ABI")
                    .setStoreClusterOrdering(1)
                    .setStoreClusterCharacteristic("Characteristics")
                    .setStoreClusterCreationDate(date)
                    .setStoreClusterValidationDate(date)
                    .build();


            List<ClusterValidated> l = new ArrayList<>();
            l.add(cluster);

            StoreClusterGroupValidated value = StoreClusterGroupValidated.newBuilder()
                    .setStoreClusterGroupIdentifier(123456)
                    .setStoreClusterGroupType("ABI")
                    .setStoreClusterGroupStartDate(date)
                    .setStoreClusterGroupEndDate(date)
                    .setStoreClusterGroupDeterminationMode("DetMode")
                    .setProductUsageCategoryIdentifier(1001)
                    .setStoreClusterGroupCreationCollaboratorIdentifier("ABI")
                    .setStoreClusterGroupCreationDate(date)
                    .setStoreClusterGroupValidationCollaboratorIdentifier("ABI")
                    .setStoreClusterGroupValidationDate(date)
                    .setProductUsageCategoryIdentifier(1234)
                    .setClusters(l)
                    .build();


            producer.sendMessage(topic, key, value, null);


        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}
