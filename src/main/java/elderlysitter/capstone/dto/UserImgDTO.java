package elderlysitter.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserImgDTO {
    private String fontIdImgUrl;
    private String backIdImgUrl;
    private String avatarImgUrl;
}
