package pl.allegro.umk.crazybill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Profile("!test")
@Configuration
public class MailConfig {
	@Value("${mail.protocol}")
	private String protocol;
	@Value("${mail.host}")
	private String host;
	@Value("${mail.port}")
	private int port;
	@Value("${mail.smtp.socketFactory.port}")
	private int socketPort;
	@Value("${mail.smtp.auth}")
	private boolean auth;
	@Value("${mail.smtp.starttls.enable}")
	private boolean starttls;
	@Value("${mail.smtp.starttls.required}")
	private boolean startlls_required;
	@Value("${mail.smtp.debug}")
	private boolean debug;
	@Value("${mail.smtp.socketFactory.fallback}")
	private boolean fallback;
	@Value("${mail.from}")
	private String from;


	@Bean
	public JavaMailSenderImpl mailSender(Environment environment) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", auth);
		mailProperties.put("mail.smtp.starttls.enable", starttls);
		mailProperties.put("mail.smtp.starttls.required", startlls_required);
		mailProperties.put("mail.smtp.socketFactory.port", socketPort);
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.debug", debug);
		mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		mailProperties.put("mail.smtp.socketFactory.fallback", fallback);

		mailSender.setJavaMailProperties(mailProperties);
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setProtocol(protocol);
		mailSender.setUsername(environment.getProperty("MAIL_USER_NAME", ""));
		mailSender.setPassword(environment.getProperty("MAIL_PASSWORD", ""));
		return mailSender;
	}

	@Bean
	public SimpleMailMessage simpleMailMessage() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setSubject("New payment");
		return simpleMailMessage;
	}
}
