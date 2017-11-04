package cap18;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

import cap03.Credentials;

public class ListarFilas implements Credentials {

	public static void main(String[] args) throws Exception {
		// Credenciais de Acesso
		BasicAWSCredentials credentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);
		AmazonSQS sqs = new AmazonSQSClient(credentials);
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sqs.setRegion(r);

		try {
			// Listar filas
			System.out.println("Qtde: " + sqs.listQueues().getQueueUrls().size());

			for (String queueUrl : sqs.listQueues().getQueueUrls()) {
				System.out.println("URL: " + queueUrl);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
