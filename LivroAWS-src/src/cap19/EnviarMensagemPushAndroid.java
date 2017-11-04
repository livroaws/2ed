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

public class EnviarMensagemPushAndroid implements Credentials {

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
		messageMap.put("gcm", getAndroidJsonMessage("Mensagem customizada para Android"));
		publishRequest.setMessageStructure("json");

		// Converte para json
		String json = toJson(messageMap);
		System.out.println(json);
		publishRequest.setMessage(json);

		// ARN do Tópico
		String deviceEndpointArn = "arn:aws:sns:sa-east-1:216082163572:endpoint/GCM/AndroidHello/da133d76-6b74-366e-b854-5bf14e6642e6";
		deviceEndpointArn = "arn:aws:sns:sa-east-1:216082163572:endpoint/GCM/AndroidHello/53435d6c-b22b-34b1-b56d-32b811d2ef85";
		publishRequest.setTargetArn(deviceEndpointArn);

		// Publica a mensagem
		PublishResult publishResult = sns.publish(publishRequest);
		System.out.println("Mensagem enviada.  MessageId=" + publishResult.getMessageId());
	}
	
	// Retorna o json para enviar a mensagem para o GCM
	protected static String getAndroidJsonMessage(String message) {
		// Parametros enviados por chave e valor para o Android (Voce vai ler chave/valor pela Intent no app)
		Map<String, String> params = new HashMap<String, String>();
		params.put("msg", message);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", params);
		map.put("collapse_key", "LivroAWS");
		map.put("delay_while_idle", true);
		map.put("time_to_live", 125);
		map.put("dry_run", false);
		String json = toJson(map);
		return json;
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
