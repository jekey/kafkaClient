package com.babyduncan.mykafka.message;

import com.babyduncan.mykafka.thrift.Domainsync;
import kafka.message.Message;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;


/**
 * User: guohaozhao (guohaozhao@sohu-inc.com)
 * Date: 13-7-11 14:24
 */
public class ThriftMessageSerializer<E extends TBase> implements Encoder<E> {

    private static final Logger logger = Logger.getLogger(ThriftMessageSerializer.class);


    @Override
    public Message toMessage(E tBase) {
        try {
            return new Message(new TSerializer().serialize(tBase));
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static Domainsync decodeDomainsync(Message message) {
        ByteBuffer byteBuffer = message.payload();

        byte[] bytes = new byte[byteBuffer.capacity()];

        byteBuffer.get(bytes);

        Domainsync _domainsync = new Domainsync();
        try {
            new TDeserializer().deserialize(_domainsync, bytes);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }

        return _domainsync;
    }

    public static void main(String... args) throws TException {
        Domainsync domainsync = new Domainsync();
        domainsync.setDomain("sohu");
        domainsync.setPassport("babyduncan@qq.com");
        domainsync.setSid(100000);
        domainsync.setType(0);

        Message message = new ThriftMessageSerializer<Domainsync>().toMessage(domainsync);

        System.out.println(message.toString());

        ByteBuffer byteBuffer = message.payload();

        byte[] bytes = new byte[byteBuffer.capacity()];

        byteBuffer.get(bytes);

        Domainsync _domainsync = new Domainsync();
        new TDeserializer().deserialize(_domainsync, bytes);

        System.out.println(ToStringBuilder.reflectionToString(_domainsync));

    }
}
