package com.babyduncan.kafkaClient.message;

import kafka.message.Message;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.serializer.StringDecoder;
import kafka.serializer.StringEncoder;

public class StringMessageSerializer implements Decoder<String>, Encoder<String> {

    private static final Encoder<String> encoder = new StringEncoder();
    private static final Decoder<String> decoder = new StringDecoder();

    @Override
    public String toEvent(Message message) {
        return decoder.toEvent(message);
    }

    @Override
    public Message toMessage(String event) {
        return encoder.toMessage(event);
    }
}
