package pl.allegro.umk.crazybill.repository;

import org.springframework.data.repository.CrudRepository;
import pl.allegro.umk.crazybill.domain.Bill;

public interface BillsRepository extends CrudRepository<Bill, String>{
}
