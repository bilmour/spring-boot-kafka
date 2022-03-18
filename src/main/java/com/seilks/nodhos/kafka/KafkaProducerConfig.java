package com.seilks.nodhos.kafka;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    @Value(value = "${bootstrap.servers}")
    private String bootstrapAddress;

    @Value(value = "${group.id}")
    private String groupId;

    @Value(value = "${security.protocol}")
    private String securityProtocol;

    @Value(value = "${ssl.endpoint.identification.algorithm}")
    private String sslEndpointIdentificationAlgorithm;

    @Value(value = "${sasl.mechanism}")
    private String saslMechanism;

    @Value(value = "${sasl.jaas.config}")
    private String saslJaasConfig;

    @Value(value = "${request.timeout.ms}")
    private String requestTimeoutMs;

    @Value(value = "${retry.backoff.ms}")
    private String retryBackoffMs;

    @Value(value = "${basic.auth.credentials.source}")
    private String basicAuthCredentialsSource;

    @Value(value = "${schema.registry.basic.auth.user.info}")
    private String schemaRegistryBasicAuthUserInfo;

    @Value(value = "${schema.registry.url}")
    private String schemaRegistryUrl;

    @Value(value = "${key.deserializer}")
    private String keyDeserializer;

    @Value(value = "${value.deserializer}")
    private String valueDeserializer;

    @Value(value = "${specific.avro.reader}")
    private String specificAvroReader;

    @Value(value = "${auto.register.schemas}")
    private String autoRegisterSchemas;

    @Bean
    public Map<String, Object> producerConfig() {

        logger.info("Init KafkaProducerConfig:{} {} {} {} {} {} {} {} {} {} {} {} {}",
                bootstrapAddress, groupId,
                securityProtocol, sslEndpointIdentificationAlgorithm,
                saslMechanism, saslJaasConfig, requestTimeoutMs,
                retryBackoffMs, basicAuthCredentialsSource, schemaRegistryBasicAuthUserInfo,
                schemaRegistryUrl, specificAvroReader, autoRegisterSchemas
        );

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put("security.protocol",securityProtocol);
        props.put("ssl.endpoint.identification.algorithm", sslEndpointIdentificationAlgorithm);
        props.put("sasl.mechanism", saslMechanism);
        props.put("sasl.jaas.config", saslJaasConfig);
        props.put("request.timeout.ms", requestTimeoutMs);
        props.put("retry.backoff.ms", retryBackoffMs);
        props.put("basic.auth.credentials.source", basicAuthCredentialsSource);
        props.put("schema.registry.basic.auth.user.info", schemaRegistryBasicAuthUserInfo);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("specific.avro.reader", specificAvroReader);
        props.put("auto.register.schemas", autoRegisterSchemas);

        return props;

    }


    @Bean
    public ProducerFactory<GenericRecord, GenericRecord> avrogProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<GenericRecord, GenericRecord> avroKafkaTemplate() {
        return new KafkaTemplate<>(avrogProducerFactory());
    }

}
