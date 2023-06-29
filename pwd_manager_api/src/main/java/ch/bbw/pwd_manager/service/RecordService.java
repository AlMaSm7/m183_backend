package ch.bbw.pwd_manager.service;

import ch.bbw.pwd_manager.repositories.RecordRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecordService {

    private final RecordRepo recordRepo;
}
