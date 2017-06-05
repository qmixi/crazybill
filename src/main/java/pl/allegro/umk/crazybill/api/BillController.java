package pl.allegro.umk.crazybill.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.allegro.umk.crazybill.BillCalculator;
import pl.allegro.umk.crazybill.api.dto.BillDto;
import pl.allegro.umk.crazybill.domain.Bill;
import pl.allegro.umk.crazybill.domain.BillResult;
import pl.allegro.umk.crazybill.repository.BillsRepository;
import pl.allegro.umk.crazybill.service.BillService;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class BillController {
    @Value("${paypal.url}")
    private String paypalUrl;
    @Value("${payments.currency}")
    private String currency;

    private final BillsRepository billsRepository;
    private final BillService billService;
    private final BillCalculator billCalculator;

    @Autowired
    public BillController(
            BillsRepository billsRepository,
            BillService billService,
            BillCalculator billCalculator
    ) {
        this.billsRepository = billsRepository;
        this.billService = billService;
        this.billCalculator = billCalculator;
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/bills/{billId}",
            produces = "application/json"
    )
    public BillDto getBill(@PathVariable String billId) {

        Bill bill = billsRepository.findOne(billId);
        if (bill == null) {
            throw new BillNotFoundException();
        }

        return bill.toDto();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/cart/{billId}",
            produces = "text/html"
    )
    public String getCart(@PathVariable String billId, Model model) {

        Bill bill = billsRepository.findOne(billId);
        if (bill == null) {
            throw new BillNotFoundException();
        }

        BillResult calculated = billCalculator.calculate(bill);
        model.addAttribute("bill", bill);
        model.addAttribute("calculated", calculated.getResultsPerPerson());
        model.addAttribute("url", paypalUrl);
        model.addAttribute("currency", currency);

        return "cart";
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/bills",
            consumes = "application/json"
    )
    public ResponseEntity createBill(@RequestBody BillDto billDto) throws URISyntaxException {
        return ResponseEntity.created(new URI("/bills/" + billService.createBill(Bill.fromDto(billDto)))).build();
    }

    @ResponseBody
    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/bills/{billId}"
    )
    public void deleteBill(@PathVariable String billId) {
        billsRepository.delete(billId);
    }
}
