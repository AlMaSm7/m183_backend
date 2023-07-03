package ch.bbw.pwd_manager.service;

import ch.bbw.pwd_manager.exceptions.InvalidOperation;
import ch.bbw.pwd_manager.model.Record;
import ch.bbw.pwd_manager.repositories.RecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecordService {

    private final RecordRepo recordRepository;
    private final EncryptionService encryptionService;

    @Autowired
    public RecordService(RecordRepo recordRepository, EncryptionService encryptionService) {
        this.recordRepository = recordRepository;
        this.encryptionService = encryptionService;
    }

    public Record createRecord(Record recordToCreate) {
        // Encrypt the password
        String encryptedPassword = encryptionService.encrypt(recordToCreate.getPassword());
        recordToCreate.setPassword(encryptedPassword);
        recordRepository.save(recordToCreate);
        // Decrypt back to response
        recordToCreate.setPassword(encryptionService.decrypt(encryptedPassword));

        return recordToCreate;
    }

    public List<Record> getRecordsByUserId(Long userId) {
        List<Record> records = recordRepository.findRecordsByUserId(userId);
        records.forEach(record -> {
            if (record != null) {
                // Decrypt the password
                String decryptedPassword = encryptionService.decrypt(record.getPassword());
                record.setPassword(decryptedPassword);
            }
        });

        return records;
    }

    public Record updateRecord(Record recordToUpdate, Long userId) {
        // Encrypt the password if it's changed
        Record existingRecord = recordRepository.findById(recordToUpdate.getRecordId()).orElse(null);
        if (existingRecord != null && !existingRecord.getPassword().equals(recordToUpdate.getPassword()) &&
                (!Objects.equals(existingRecord.getUser().getUserId(), userId))) {
            String encryptedPassword = encryptionService.encrypt(recordToUpdate.getPassword());
            recordToUpdate.setPassword(encryptedPassword);
        } else {
            throw new InvalidOperation("User is not allowed to change this password");
        }

        recordRepository.save(recordToUpdate);
        recordToUpdate.setPassword(encryptionService.decrypt(recordToUpdate.getPassword()));
        return recordToUpdate;
    }

    public String deleteRecord(Long id, Long userId) {
        Optional<Record> record = recordRepository.findById(id);
        if (record.isPresent()) {
            if (Objects.equals(record.get().getUser().getUserId(), userId)) {
                recordRepository.deleteById(id);
                return "deleted";
            } else {
                return "You cannot change another users password";
            }
        } else {
            return "not found";
        }
    }
}
