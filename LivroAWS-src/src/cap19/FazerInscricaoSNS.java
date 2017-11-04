package cap19;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

import cap03.Credentials;

public class FazerInscricaoSNS implements Credentials {

	public static void main(String[] args) {
		// Credenciais de Acesso
		BasicAWSCredentials credentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);
		// SNS
		AmazonSNSClient sns = new AmazonSNSClient(credentials);
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sns.setRegion(r);
		
		String topicARN = "arn:aws:sns:sa-east-1:914110320306:LivroAWSTopic";
		
		// Configurar a inscrição
		SubscribeRequest subscribeRequest = new SubscribeRequest();
		subscribeRequest.setTopicArn(topicARN);
		subscribeRequest.setProtocol("email");
		subscribeRequest.setEndpoint("rlecheta@livetouch.com.br");

		// Fazer a inscrição
		SubscribeResult result = sns.subscribe(subscribeRequest);
		
		System.out.println("Inscrição enviada, aguardando confirmação: " + result);
	}
}
