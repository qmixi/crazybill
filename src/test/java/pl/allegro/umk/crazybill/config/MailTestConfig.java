package pl.allegro.umk.crazybill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Profile("test")
@Configuration
public class MailTestConfig {
    @Value("${mail.from}")
    private String from;


    @Bean
    public JavaMailSenderImpl mailSender(Environment environment) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();

        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost("localhost");
        mailSender.setPort(65438);
        mailSender.setUsername("dummyUser");
        mailSender.setPassword("password");
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
