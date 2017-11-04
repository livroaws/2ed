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
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;

import cap03.Credentials;

/**
 * @author Ricardo Lecheta
 *
 */
public class EnviarMensagensLote implements Credentials {

	public static void main(String[] args) throws Exception {
		// Credenciais de Acesso
		BasicAWSCredentials credentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);

		AmazonSQS sqs = new AmazonSQSClient(credentials);
		
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sqs.setRegion(r);

		System.out.println("===========================================");
		System.out.println("Enviando uma mensagem para o Amazon SQS");
		System.out.println("===========================================\n");

		try {
			// URL da fila
			String url = "https://sqs.sa-east-1.amazonaws.com/914110320306/HelloSQS";

			// Cria a lista com as mensagens
			List<SendMessageBatchRequestEntry> mensagens = new ArrayList<SendMessageBatchRequestEntry>();
			mensagens.add(new SendMessageBatchRequestEntry("1", "Oi Fila Mensagem lote 1."));
			mensagens.add(new SendMessageBatchRequestEntry("2", "Oi Fila Mensagem lote 2."));
			mensagens.add(new SendMessageBatchRequestEntry("3", "Oi Fila Mensagem lote 3."));

			// Envia o lote
			sqs.sendMessageBatch(new SendMessageBatchRequest(url, mensagens));

			System.out.println("Mensagens enviadas com sucesso");

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}
	}
}
