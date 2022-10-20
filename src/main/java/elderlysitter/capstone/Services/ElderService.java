package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.ElderDTO;
import elderlysitter.capstone.entities.Elder;

import java.util.List;

public interface ElderService {
    List<Elder> getAllElderByCustomer(String email);

    Elder addElder(ElderDTO elderDTO);
}
