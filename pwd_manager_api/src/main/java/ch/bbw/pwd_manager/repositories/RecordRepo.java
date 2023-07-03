package ch.bbw.pwd_manager.repositories;

import ch.bbw.pwd_manager.model.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepo extends CrudRepository<Record, Long> {
    @Query("SELECT r FROM Record r WHERE r.user.userId = :userId")
    List<Record> findRecordsByUserId(@Param("userId") Long userId);
}
