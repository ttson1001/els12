package elderlysitter.capstone.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRequestDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String url;
    private String sitterRequirement;
    private Integer duration;
    private Long statusID;
    private Long categoryID;


}
