package ch.bbw.pwd_manager.repositories;

import ch.bbw.pwd_manager.model.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepo extends CrudRepository<Record, Long> {
}
