package cap17;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class HelloSMTP {
	public static void main(String[] args) {
		String to = "rlecheta@livetouch.com.br";
		String from = "rlecheta@gmail.com";
		String subject = "Teste email AWS SMTP";
		String msg = "Oi Ricardo AWS";
		String smtp = "email-smtp.us-east-1.amazonaws.com";

		/**
		 * Smtp Username,Smtp Password
		 */
		String smtpUser = "AKIAIC6IEFNIJVXPFRNA";
		String smtpPassword = "Aq8aBYXoHbgoqFYHiZnoWnY09HH46vPzIeCECsU0k4vb";

		System.out.println("Enviando email para: " + to);
		System.out.println("Email: " + msg);

		try {
			Email email = new SimpleEmail();
			email.setHostName(smtp);
			email.setSmtpPort(25);
			email.setAuthenticator(new DefaultAuthenticator(smtpUser, smtpPassword));
			email.setFrom(from);
			email.addTo(to);
			email.setSubject(subject);
			email.setMsg(msg);
			email.send();

			System.out.println("Email enviado com sucesso");
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}