package pub.developers.forum.api.request.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidCodeRequest extends UserBaseLoginRequest {

    private String email;

    private String phone;

    private String imageCode;
}
