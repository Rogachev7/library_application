package rogachev7.library_application.model.DTO;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

    private String login;
    private String password;
}
