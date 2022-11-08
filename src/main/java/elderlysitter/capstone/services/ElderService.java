package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddElderRequestDTO;
import elderlysitter.capstone.dto.request.UpdateElderRequestDTO;
import elderlysitter.capstone.entities.Elder;

import java.util.List;

public interface ElderService {
    Elder addElder(AddElderRequestDTO addElderRequestDTO);

    Elder updateElder(UpdateElderRequestDTO updateElderRequestDTO);

    Elder removeElder(Long id);

    List<Elder> getAllElderByCustomerEmail(String email);

    Elder getElderById(Long id);
}
