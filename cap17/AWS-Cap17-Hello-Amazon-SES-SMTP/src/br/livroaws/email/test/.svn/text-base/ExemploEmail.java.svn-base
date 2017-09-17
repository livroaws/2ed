package br.livroaws.email.test;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ExemploEmail {
	public static void main(String[] args) {
		String to = "seu_email@gmail.com";
		String from = "seu_email@gmail.com";
		String subject = "Teste email AWS java";
		String msg = "Oi Ricardo AWS";
		String smtp = "email-smtp.us-east-1.amazonaws.com";

		/**
		 * Smtp Username,Smtp Password
		 */
		String smtpUser = "usuario_smtp_aqui";
		String smtpPassword = "senha_smtp_aqui";

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