package com.relay42.sensor.consumer;

import com.relay42.sensor.service.dto.event.EventModel;
import com.relay42.sensor.service.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.Base64;


@Component
public class JsonDecoderPayloadReader implements PayloadReader {

	@Override
	public boolean support(String contentType, Object payload) {
		return (payload instanceof String) && "application/json".equals(contentType);
	}

	@Override
	public EventModel read(Object payload) {
		String body = "" + payload;
		// let's handle both encoded string and json
		if (JsonUtil.isJson(body) == false) {
			// TODO: find out where it is encoding to base64
			body = decode(body);
		}
		return JsonUtil.toObject(body, EventModel.class);
	}

	public String decode(String payload) {
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String val = new String(decoder.decode(payload.replaceAll("\"", "")));
		return val;
	}

}
