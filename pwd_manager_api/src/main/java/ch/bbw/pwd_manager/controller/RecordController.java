package ch.bbw.pwd_manager.controller;

import ch.bbw.pwd_manager.config.JwtService;
import ch.bbw.pwd_manager.exceptions.InvalidOperation;
import ch.bbw.pwd_manager.model.Record;
import ch.bbw.pwd_manager.model.User;
import ch.bbw.pwd_manager.service.RecordService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecordController {

    private final RecordService recordService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Record>> getRecords(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        Long userId = getUserId(token);
        List<Record> records = recordService.getRecordsByUserId(userId);
        return ResponseEntity.ok().body(records);
    }

    @PostMapping
    public ResponseEntity<Record> createRecordToUser(@RequestBody Record record, HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        User user = new User();
        user.setUserId(getUserId(token));
        record.setUser(user);
        Record createdRecord = recordService.createRecord(record);
        return ResponseEntity.ok().body(createdRecord);
    }

    @PutMapping
    public ResponseEntity<Record> updateRecord(@RequestBody Record record, HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        User user = new User();
        user.setUserId(getUserId(token));
        record.setUser(user);
        Record updateRecord = recordService.updateRecord(record, getUserId(token));
        return ResponseEntity.ok().body(updateRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id, HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        String result = recordService.deleteRecord(id, getUserId(token));
        if(result.equals("deleted")){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else if(result.equals("You cannot change another users password")){
            throw new InvalidOperation(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String token = null;
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        return token;
    }

    private Long getUserId(String token) {
        Claims claims = jwtService.extractAllClaims(token);
        Integer userIdInteger = (Integer) claims.get("userId");
        return userIdInteger.longValue();
    }

    @ExceptionHandler(InvalidOperation.class)
    public ResponseEntity<String> handleLoginFailedException(InvalidOperation ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
