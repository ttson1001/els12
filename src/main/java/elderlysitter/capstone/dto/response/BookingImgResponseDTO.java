package elderlysitter.capstone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingImgResponseDTO {

    private LocalDateTime localDateTime;

    private String url;
//
//    private String checkInUrl;
//    private String checkOutUrl;
}
