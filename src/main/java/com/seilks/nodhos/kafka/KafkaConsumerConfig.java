package com.seilks.nodhos.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

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
    public Map<String, Object> consumersConfig() {

        logger.info("Init KafkaConsumerConfig:{} {} {} {} {} {} {} {} {} {} {} {} {}",
                bootstrapAddress, groupId,
                securityProtocol, sslEndpointIdentificationAlgorithm,
                saslMechanism, saslJaasConfig, requestTimeoutMs,
                retryBackoffMs, basicAuthCredentialsSource, schemaRegistryBasicAuthUserInfo,
                schemaRegistryUrl, specificAvroReader, autoRegisterSchemas
        );

        Map<String, Object> props = new HashMap<> ();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
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
    public ConsumerFactory<GenericRecord, GenericRecord> consumerFactory() {
        //  final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        // jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>( consumersConfig() );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<GenericRecord, GenericRecord> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<GenericRecord , GenericRecord> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        //    factory.setErrorHandler(errorHandler);

        return factory;
    }



}

