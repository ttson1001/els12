package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.ElderDTO;
import elderlysitter.capstone.dto.ElderProfileDTO;
import elderlysitter.capstone.entities.Elder;

import java.util.List;

public interface ElderService {
    List<Elder> getAllElderByCustomer(String email);

    Elder addElder(ElderDTO elderDTO);

    Elder updateElder(ElderProfileDTO elderProfileDTO);
    Elder getElderById(Long id);

    Elder removeElder(Long id);


}
