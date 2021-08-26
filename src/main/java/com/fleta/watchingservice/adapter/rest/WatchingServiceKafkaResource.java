package com.fleta.watchingservice.adapter.rest;

import com.fleta.watchingservice.config.KafkaProperties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/watching-service-kafka")
public class WatchingServiceKafkaResource {

    private final Logger log = LoggerFactory.getLogger(WatchingServiceKafkaResource.class);

    private final KafkaProperties kafkaProperties;
    Producer<String, String> producer;

    public WatchingServiceKafkaResource(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
        this.producer = new KafkaProducer<String, String>(kafkaProperties.getProducerProps());
    }

    @PostMapping("/publish/{topic}")
    public PublishResult publish(
            @PathVariable String topic,
            @RequestParam String message,
            @RequestParam(required = false) String key
    ) {
        long time = System.currentTimeMillis();
        log.debug("REST request to send to Kafka topic {} with key {} the message : {}", topic, key, message);
        RecordMetadata metadata = null;
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
            metadata = producer.send(record).get();
            long elapsedTime = System.currentTimeMillis() - time;
            log.info("sent record(key={} value={}) meta(partition={}, offset={}) time={}\n",
                    record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            producer.flush();
        }

        assert metadata != null;
        return new PublishResult(metadata.topic(), metadata.partition(), metadata.offset(), Instant.ofEpochMilli(metadata.timestamp()));
    }

    @GetMapping("/consume")
    public List<String> consume(@RequestParam("topic") List<String> topics, @RequestParam Map<String, String> consumerParams) {
        log.debug("REST request to consume records from Kafka topics {}", topics);
        Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();
        consumerProps.putAll(consumerParams);
        consumerProps.remove("topic");
        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(topics);

        final int giveUp = 100;
        int noRecordsCount = 0;
        ConsumerRecords<String, String> consumerRecords = null;
        try {
            while (true) {
                consumerRecords = consumer.poll(Integer.MAX_VALUE);
                if (consumerRecords.count() == 0) {
                    noRecordsCount++;
                    if (noRecordsCount > giveUp) break;
                    else continue;
                }
                break;
            }
            log.info("consumer commitAsync...");
            consumer.commitAsync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("consumer closing...");
            consumer.close();
        }

        if (Objects.isNull(consumerRecords)) {
            return new ArrayList<>();
        }

        consumerRecords.forEach(record -> log.info("Consumer Record:({}, {}, {}, {})\n",
                record.key(), record.value(), record.partition(), record.offset()));
        return StreamSupport
                .stream(consumerRecords.spliterator(), false)
                .map(ConsumerRecord::value)
                .collect(Collectors.toList());
    }

    private static class PublishResult {

        public final String topic;
        public final int partition;
        public final long offset;
        public final Instant timestamp;

        private PublishResult(String topic, int partition, long offset, Instant timestamp) {
            this.topic = topic;
            this.partition = partition;
            this.offset = offset;
            this.timestamp = timestamp;
        }
    }
}
