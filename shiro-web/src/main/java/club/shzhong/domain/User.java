package club.shzhong.domain;

import lombok.Data;

@Data
public class User {
    public String username;
    public String password;
    private Boolean rememberMe;
}
