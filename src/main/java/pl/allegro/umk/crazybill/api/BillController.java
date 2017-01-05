package pl.allegro.umk.crazybill.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.umk.crazybill.api.dto.BillDto;

import java.util.ArrayList;

@RestController
public class BillController {

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/bills/billId",
            produces = "application/json"
    )
    public BillDto getBill() {
        return new BillDto("billId", "bill name", new ArrayList<>());
    }

}
