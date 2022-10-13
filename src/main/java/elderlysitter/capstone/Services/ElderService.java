package elderlysitter.capstone.Services;

import elderlysitter.capstone.dto.ElderDTO;
import elderlysitter.capstone.entities.Elder;

import java.util.List;

public interface ElderService {
    List<Elder> getAllElder();

    Elder addElder(ElderDTO elderDTO);
}
