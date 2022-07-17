package com.relay42.sensor;

import com.relay42.sensor.consumer.JsonDecoderPayloadReader;
import com.relay42.sensor.consumer.PayloadReader;
import com.relay42.sensor.service.dto.event.EventModel;
import com.relay42.sensor.util.EventSub;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class PayloadReaderTest {

	
	
	@Test
    void test_json_decoder() {
        PayloadReader pr = new JsonDecoderPayloadReader();
        String payload = "eyJpZCI6MSwidHlwZSI6IlRFTVBFUkFUVVJFIiwibmFtZSI6IkxpdmluZyBSb29tIFRlbXAiLCJjbHVzdGVySWQiOjEsInRpbWVzdGFtcCI6IjIwMjItMDctMTZUMTQ6NDk6MTkuMjY3Njc4WiIsInZhbHVlIjo1MC4wMjAxMTQ2MjY1MzQ1NjQsImluaXRpYWxpemVkIjp0cnVlfQ==";
        boolean support = pr.support("application/json", payload);
        Assert.isTrue(support);
        EventModel read = pr.read(payload);
        Assert.isTrue(read.getId().intValue() == 1);
        
    }
	
	
	@Test()
    void test_json_decoder_not_supported() {
        PayloadReader pr = new JsonDecoderPayloadReader();
        String payload = "ewogICAgICAgICJ0b3RhbCI6IDEyMCwKICAgICAgICAidHlwZSI6ICJURU1QRVJBVFVSRSIsCiAgICAgICAgImhlYXJ0QmVhdCI6IDUsCiAgICAgICAgImlkIjogMSwKICAgICAgICAibmFtZSI6ICJMaXZpbmcgUm9vbSBUZW1wIiwKICAgICAgICAiY2x1c3RlcklkIjogIjEiCiAgICB9";
        boolean support = pr.support("application/json", new EventSub());
        Assert.isTrue(!support);
        
    }
	
	@Test
    void testPlainJsonDecoder() {
        PayloadReader pr = new JsonDecoderPayloadReader();

        String payload = "{\r\n"
        		+ "  \"total\": 120,\r\n"
        		+ "  \"type\": \"TEMPERATURE\",\r\n"
        		+ "  \"heartBeat\": 5,\r\n"
        		+ "  \"id\": 1,\r\n"
        		+ "  \"name\": \"Living Room Temp\",\r\n"
        		+ "  \"clusterId\": \"1\"\r\n"
        		+ "}";
        boolean support = pr.support("application/json", payload);
        Assert.isTrue(support);
        EventModel read = pr.read(payload);
        Assert.isTrue(read.getId().intValue() == 1);
    }
	
}
