package com.IPS.ForB.Conversion;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.gson.Gson;
import com.google.pubsub.v1.PubsubMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileConversionResultHandler implements BackgroundFunction<PubsubMessage> {

	@Override
	public void accept(PubsubMessage message, Context context) {
		try {
			Gson gson = new Gson();
			FileConversionResult result = gson.fromJson(message.getData().toStringUtf8(), FileConversionResult.class);

			// 파일 변환 결과 처리 (DB 업데이트 등)
			if (result.getStatus().equals("SUCCESS")) {
				Long fileId = result.getFileId();
				// 파일 변환 성공 시 처리
			} else {
				// 파일 변환 실패 시 처리
			}

			// 메시지 처리 완료
			AckReplyConsumer consumer = context.newAckReplyConsumer();
			consumer.ack();
		} catch (Exception e) {
			log.error("Failed to process conversion result message", e);
			AckReplyConsumer consumer = context.newAckReplyConsumer();
			consumer.nack();
		}
	}
}
