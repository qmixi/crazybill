package pl.allegro.umk.crazybill.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import pl.allegro.umk.crazybill.BillCalculator;
import pl.allegro.umk.crazybill.domain.Bill;
import pl.allegro.umk.crazybill.repository.BillsRepository;

@Service
public class BillService {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BillService.class);
	private final BillsRepository billsRepository;
	private final BillCalculator billCalculator;
	private final MailSender mailSender;
	private final SimpleMailMessage templateMessage;

	@Autowired
	public BillService(BillsRepository billsRepository,
					   BillCalculator billCalculator,
					   MailSender mailSender,
					   SimpleMailMessage templateMessage) {
		this.billsRepository = billsRepository;
		this.billCalculator = billCalculator;
		this.mailSender = mailSender;
		this.templateMessage = templateMessage;
	}

	public String createBill(Bill bill) {
		Bill savedBill = billsRepository.save(bill);
		billCalculator
				.calculate(bill)
				.getCalculatedResults()
				.forEach(billPerPerson -> {
					notifyUser(billPerPerson.getKey(), billPerPerson.getValue(), bill.getName());
				});
		return savedBill.getId();
	}

	private void notifyUser(String user, Double billAmount, String billName) {
		SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
		msg.setTo(user);
		msg.setText(String.format("Hello, \n\n" +
				"You had a pleasure make a shopping/eating together with friends. \n" +
				"You have made shopping/eating for amount: %f PLN. The bill name is: %s. Please give back money to your friend. \n\n" +
				"Yours Crazy Bill", billAmount, billName));
		try{
			mailSender.send(msg);
		}
		catch (MailException ex) {
			logger.error("Could not send notification to the user. Error: " + ex.getMessage());
		}
	}
}
