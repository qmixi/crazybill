package pl.allegro.umk.crazybill.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.umk.crazybill.api.dto.BillDto;
import pl.allegro.umk.crazybill.api.dto.PersonDto;
import pl.allegro.umk.crazybill.api.dto.PositionDto;
import pl.allegro.umk.crazybill.domain.Bill;
import pl.allegro.umk.crazybill.domain.BillPosition;
import pl.allegro.umk.crazybill.repository.BillsRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class BillController {

    private final BillsRepository billsRepository;

    @Autowired
    public BillController(BillsRepository billsRepository) {
        this.billsRepository = billsRepository;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/bills/{billId}",
            produces = "application/json"
    )
    public BillDto getBill(@PathVariable String billId) {

        Bill bill = billsRepository.findOne(billId);
        if (Objects.isNull(bill)) {
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
        Bill bill = Bill.fromDto(billDto);
        billsRepository.save(bill);
        return ResponseEntity.created(new URI("/bills/" + bill.getId())).build();
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/bills/{billId}"
    )
    public void deleteBill(@PathVariable String billId) {
        billsRepository.delete(billId);
    }
}
