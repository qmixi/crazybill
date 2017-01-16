package pl.allegro.umk.crazybill.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BillNotFoundException extends RuntimeException {
    BillNotFoundException() {
        super("Bill not found");
    }
}