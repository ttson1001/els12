package elderlysitter.capstone.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import elderlysitter.capstone.entities.BookingDetail;
import elderlysitter.capstone.entities.Category;
import elderlysitter.capstone.entities.SitterService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddServiceRequestDTO {
    private String name;
    private BigDecimal price;
    private String description;
    private String url;
    private String sitterRequirement;
    private Integer duration;
    private String commission;
    private Long categoryId;
}
