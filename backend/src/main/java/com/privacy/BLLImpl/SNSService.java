package com.privacy.BLLImpl;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sns.model.UnsubscribeRequest;

@Service
public class SNSService {

	private final SnsClient snsClient;
	
	public SNSService(SnsClient snsClient) {
		this.snsClient = snsClient;
	}
	
	public String createTopic(String topicName) {
		CreateTopicResponse response = snsClient.createTopic(CreateTopicRequest.builder().name(topicName).build());
		return response.topicArn();
	}
	
	public String subscribeEmail(String topicArn, String email)
	{
		SubscribeResponse response = snsClient.subscribe(
				SubscribeRequest.builder()
				.protocol("email")
				.endpoint(email)
				.returnSubscriptionArn(true)
				.topicArn(topicArn)
				.build());
		
		return response.subscriptionArn();
	}
	
	public void publishMessage(String topicArn, String subject, String message)
	{
		snsClient.publish(
				PublishRequest.builder()
				.topicArn(topicArn)
				.subject(subject)
				.message(message)
				.build());
	}
	
	public void unsubscribe(String subscriptionArn) {
	    snsClient.unsubscribe(UnsubscribeRequest.builder()
	            .subscriptionArn(subscriptionArn)
	            .build());
	}
}
