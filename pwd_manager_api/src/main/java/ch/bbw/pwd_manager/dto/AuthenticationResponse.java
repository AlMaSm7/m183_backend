package ch.bbw.pwd_manager.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AuthenticationResponse {
    private String token;
}
