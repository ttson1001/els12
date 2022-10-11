package elderlysitter.capstone.dto;

import com.sun.net.httpserver.Authenticator;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private SuccessCode successCode;
    private ErrorCode errorCode;
    private Object data;
}
