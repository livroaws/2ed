package cap18;

/*

 * Copyright 2010-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import cap03.Credentials;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
 * account, and be signed up to use Amazon SQS. For more information on Amazon
 * SQS, see http://aws.amazon.com/sqs.
 * <p>
 * <b>Important:</b> Be sure to fill in your AWS access credentials in the
 * AwsCredentials.properties file before you try to run this sample.
 * http://aws.amazon.com/security-credentials
 */
public class LerMensagens implements Credentials {

	public static void main(String[] args) throws Exception {
		// Credenciais de Acesso
		BasicAWSCredentials credentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);

		AmazonSQS sqs = new AmazonSQSClient(credentials);
		
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sqs.setRegion(r);

		System.out.println("Recebendo a mensagem da Amazon SQS...");

		try {
			// URL da fila
			String url = "https://sqs.sa-east-1.amazonaws.com/914110320306/HelloSQS";

			// Recebe as mensagens
			ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(url);
			// Máximo 10 mensagens
			receiveMessageRequest.setMaxNumberOfMessages(10); 
			// Aguarda 5 segundos as mensagens
			receiveMessageRequest.setWaitTimeSeconds(5); 
			
			// Lista
			List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
			System.out.println("Mensagens recebidas: " + messages.size());
			for (Message message : messages) {
				System.out.println("  Message");
				System.out.println("    MessageId:     " + message.getMessageId());
				System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
				System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
				System.out.println("    Body:          " + message.getBody());
				for (Entry<String, String> entry : message.getAttributes().entrySet()) {
					System.out.println("  Attribute");
					System.out.println("    Name:  " + entry.getKey());
					System.out.println("    Value: " + entry.getValue());
				}

				// Deleta a mensagem
				String messageRecieptHandle = message.getReceiptHandle();
				sqs.deleteMessage(new DeleteMessageRequest(url, messageRecieptHandle));
			}
			System.out.println();
		} catch (Exception ase) {
			ase.printStackTrace();
		}
	}
}
