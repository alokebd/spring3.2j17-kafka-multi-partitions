package com.tech2java.kafka.customconsumer.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessageMetaData {
    private String messageId;
    private String SourceOriginator;
    private Date timestamp;
}
