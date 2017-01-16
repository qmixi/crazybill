package pl.allegro.umk.crazybill.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.umk.crazybill.api.dto.BillDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class BillController {

    private final Map<String, BillDto> bills = new ConcurrentHashMap<>();

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/bills/{billId}",
            produces = "application/json"
    )
    public BillDto getBill(@PathVariable String billId) {
        if (!bills.containsKey(billId)) {
            throw new BillNotFoundException();
        }
        return bills.get(billId);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/bills",
            consumes = "application/json"
    )
    public ResponseEntity createBill(@RequestBody BillDto billDto) throws URISyntaxException {
        String billId = UUID.randomUUID().toString();

        bills.put(billId, new BillDto(billId, billDto.getName(), billDto.getPositions()));
        return ResponseEntity.created(new URI("/bills/" + billId)).build();
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/bills/{billId}"
    )
    public void deleteBill(@PathVariable String billId) {
        bills.remove(billId);
    }
}
