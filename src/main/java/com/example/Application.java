package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class Application {

    public static final String TOPIC = "room-events";

    public static void main(String[] args) throws InterruptedException {

            String root = System.getProperty("user.dir");
            root += "/src/main/resources/";

            Properties props = new Properties();
            props.put("bootstrap.servers", "iotkafka-amit007-6331.aivencloud.com:22622");
            props.put("security.protocol", "SSL");
            props.put("ssl.truststore.location", root+"client.truststore.jks");
            props.put("ssl.truststore.password", "secret");
            props.put("ssl.keystore.type", "PKCS12");
            props.put("ssl.keystore.location", root+"client.keystore.p12");
            props.put("ssl.keystore.password", "secret");
            props.put("ssl.key.password", "secret");
            props.put("key.serializer", KafkaJsonSerializer.class.getName());
            props.put("value.serializer", KafkaJsonSerializer.class.getName());

            KafkaProducer<Key, Event> producer = new KafkaProducer<>(props);

            List<String> location = Arrays.asList("kitchen", "living", "dining");

            try {
                while (true) {
                    Event Event = getEvent(location);
                    producer.send(new ProducerRecord<Key, Event>(TOPIC, new Key(), Event));
                    Thread.sleep(1000);
                }
            } finally {
                producer.close();
            }
        }

        private static Event getEvent(List<String> location) {
            return Event.builder().id(location.get(getRandomNumberUsingInts(0, 3)))
                    .time( DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()))
                    .temperature(getRandomNumberUsingInts(50, 80))
                    .humidity(getRandomNumberUsingInts(20, 40))
                    .build();
        }

        public static int getRandomNumberUsingInts(int min, int max) {
            Random random = new Random();
            return random.ints(min, max)
                    .findFirst()
                    .getAsInt();
        }
}
