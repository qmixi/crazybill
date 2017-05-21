package pl.allegro.umk.crazybill.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.allegro.umk.crazybill.api.dto.BillDto;
import pl.allegro.umk.crazybill.domain.Bill;
import pl.allegro.umk.crazybill.repository.BillsRepository;
import pl.allegro.umk.crazybill.service.BillService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class BillController {

    private final BillsRepository billsRepository;
    private final BillService billService;

    @Autowired
    public BillController(BillsRepository billsRepository, BillService billService) {
        this.billsRepository = billsRepository;
        this.billService = billService;
    }

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
            method = RequestMethod.POST,
            path = "/bills",
            consumes = "application/json"
    )
    public ResponseEntity createBill(@RequestBody BillDto billDto) throws URISyntaxException {
        return ResponseEntity.created(new URI("/bills/" + billService.createBill(Bill.fromDto(billDto)))).build();
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/bills/{billId}"
    )
    public void deleteBill(@PathVariable String billId) {
        billsRepository.delete(billId);
    }
}
