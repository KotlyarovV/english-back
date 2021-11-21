package english.web.api.dto;

public class AuthSuccessDto {
    private UserInfoDto userInfoDto;

    private String token;

    public AuthSuccessDto(UserInfoDto userInfoDto, String token) {
        this.token = token;
        this.userInfoDto = userInfoDto;
    }

    public UserInfoDto getUserInfoDto() {
        return userInfoDto;
    }

    public String getToken() {
        return token;
    }
}
