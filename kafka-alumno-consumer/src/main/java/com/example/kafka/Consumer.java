package com.example.kafka;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.AlumnoDTO;
import com.example.service.MainService;

@Service
public class Consumer {
    
    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private MainService mainService;

    @KafkaListener(topics = "${app.topic.alumnos}", containerFactory = "alumnoListener")
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void alumnos(
        @Payload List<AlumnoDTO> alumnos, 
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) int offset,
        Acknowledgment ack
        ) throws InterruptedException, ExecutionException, TimeoutException {
        this.mainService.saveAlumno(alumnos);
        LOG.info("ctas received from partition-{} with offset-{}", partition, offset);
        ack.acknowledge();
    }


}