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
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;

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
public class EnviarMensagens implements Credentials {

	public static void main(String[] args) throws Exception {
		// Credenciais de Acesso
		BasicAWSCredentials credentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);

		AmazonSQS sqs = new AmazonSQSClient(credentials);
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sqs.setRegion(r);

		System.out.println("Enviando uma mensagem para o Amazon SQS...");

		try {
			// URL da fila
			String url = "https://sqs.sa-east-1.amazonaws.com/914110320306/HelloSQS";

			// Envia a mensagem
			sqs.sendMessage(new SendMessageRequest(url, "Oi Fila Mensagem 1."));
			sqs.sendMessage(new SendMessageRequest(url, "Oi Fila Mensagem 2."));
			sqs.sendMessage(new SendMessageRequest(url, "Oi Fila Mensagem 3."));

			System.out.println("Mensagens enviadas com sucesso.");

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}
	}
}
