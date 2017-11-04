package cap17;

/*
 * Copyright 2011-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AWSJavaMailTransport;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;

import cap03.Credentials;

/**
 * This sample demonstrates how to make basic requests to the Amazon Simple
 * Email Service using the the standard JavaMail API.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
 * account, and be signed up to use Amazon Simple Email Service. For more
 * information on Amazon Simple Email Service, see http://aws.amazon.com/ses .
 * <p>
 * <b>Important:</b> Be sure to fill in your AWS access credentials in the
 * AwsCredentials.properties file before you try to run this sample.
 * http://aws.amazon.com/security-credentials
 */
public class HelloSES implements Credentials {
	// Configure os emails "to" e "from"
	private static final String TO = "rlecheta@gmail.com";
	private static final String FROM = "rlecheta@gmail.com";
	private static final String BODY = "Hello World SES!";
	private static final String SUBJECT = "Hello World from API!";

	public static void main(String[] args) throws IOException {
		// Cria a classe AmazonSimpleEmailService com as credenciais de acesso
		BasicAWSCredentials credentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);

		AmazonSimpleEmailService ses = new AmazonSimpleEmailServiceClient(credentials);
		Region r = Region.getRegion(Regions.US_EAST_1);
		ses.setRegion(r);

		// No modo Sandbox, é preciso validar os emails "from" e "to"
		verifyEmailAddress(ses, FROM);
		verifyEmailAddress(ses, TO);

		/*
		 * Configura o JavaMail para utilizar o Amazon SES.
		 */
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "aws");

		// Informa as chaves de acesso da AWS
		props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
		props.setProperty("mail.aws.password", credentials.getAWSSecretKey());

		Session session = Session.getInstance(props);

		try {
			// Dados da mensagem
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setSubject(SUBJECT);
			msg.setText(BODY);
			msg.saveChanges();

			// Envia o email
			Transport t = new AWSJavaMailTransport(session, null);
			t.connect();
			t.sendMessage(msg, null);

			// Fecha a conexao de envio
			t.close();
		} catch (Exception e) {
			e.printStackTrace(); // Tratar erros aqui
		}
	}

	// Verifica se o e-mail está validado no modo de acesso Sandbox
	private static void verifyEmailAddress(AmazonSimpleEmailService ses, String address) {
		ListVerifiedEmailAddressesResult verifiedEmails = ses.listVerifiedEmailAddresses();
		if (verifiedEmails.getVerifiedEmailAddresses().contains(address)) {
			// OK
			return;
		}
		// Envia o email de validação
		ses.verifyEmailAddress(new VerifyEmailAddressRequest().withEmailAddress(address));
		System.exit(0);
	}
}
