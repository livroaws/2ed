import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

public class FazerInscricaoSNS {

	public static void main(String[] args) {
		// SNS
		AWSCredentials credentials = new ClasspathPropertiesFileCredentialsProvider().getCredentials(); // Lê o arquivo AwsCredentials.properties
		AmazonSNSClient sns = new AmazonSNSClient(credentials);
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sns.setRegion(r);
		
		String topicARN = "arn:aws:sns:sa-east-1:216082163572:LivroAWSTopic";
		
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
