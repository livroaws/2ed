package cap19;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import cap03.Credentials;

public class EnviarMensagemSNS implements Credentials {

	public static void main(String[] args) {
		// Credenciais de Acesso
		BasicAWSCredentials credentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);
		// SNS
		AmazonSNSClient sns = new AmazonSNSClient(credentials);
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sns.setRegion(r);

		// Cria a mensagem
		PublishRequest publishRequest = new PublishRequest();
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put("default", "Default message");
		messageMap.put("sqs", "Mensagem customizada para o SQS");
		messageMap.put("email", "Mensagem customizada para email");
		publishRequest.setMessageStructure("json");

		// Converte para json
		String json = toJson(messageMap);
		System.out.println(json);
		publishRequest.setMessage(json);

		// ARN do Tópico
		String topicARN = "arn:aws:sns:sa-east-1:914110320306:LivroAWSTopic";
		publishRequest.setTargetArn(topicARN);

		// Publica a mensagem
		PublishResult publishResult = sns.publish(publishRequest);
		System.out.println("Mensagem enviada.  MessageId=" + publishResult.getMessageId());
	}

	protected static String toJson(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw (RuntimeException) e;
		}
	}
}
