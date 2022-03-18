package com.seilks.nodhos;

import com.adeo.offer.cluster.events.cluster.model.v2.ClusterValidated;
import com.adeo.offer.cluster.events.cluster.model.v2.StoreClusterGroupValidated;
import com.adeo.offer.cluster.events.cluster.model.v2.StoreClusterGroupValidatedKey;
import com.seilks.nodhos.kafka.KafkaProducer;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class KafkaApplication  extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

}
