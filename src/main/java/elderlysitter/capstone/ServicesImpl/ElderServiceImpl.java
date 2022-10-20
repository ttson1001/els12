package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.ElderService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ElderDTO;
import elderlysitter.capstone.dto.ElderProfileDTO;
import elderlysitter.capstone.entities.Elder;
import elderlysitter.capstone.repository.ElderRepository;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ElderServiceImpl implements ElderService {

    @Autowired
    ElderRepository elderRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Elder> getAllElderByCustomer(String email) {
        List<Elder> elders = elderRepository.findAllByUser_Email(email);
        return elders;
    }

    @Override
    public Elder addElder(ElderDTO elderDTO) {
        Elder elder = Elder.builder()
                .name(elderDTO.getName())
                .note(elderDTO.getNote())
                .dob(elderDTO.getDob())
                .healthStatus(elderDTO.getHealthStatus())
                .isAllergy(elderDTO.getIsAllergy())
                .user(userRepository.findUserByEmail(elderDTO.getEmail()))
                .build();
        return elderRepository.save(elder);
    }

    @Override
    public Elder updateElder(ElderProfileDTO elderProfileDTO) {
        Elder elder = elderRepository.findById(elderProfileDTO.getId()).get();
        elder.setName(elderProfileDTO.getName());
        elder.setDob(elderProfileDTO.getDob());
        elder.setGender(elder.getGender());
        elder.setNote(elderProfileDTO.getNote());
        elder.setHealthStatus(elderProfileDTO.getHealthStatus());
        elder.setIsAllergy(elderProfileDTO.getIsAllergy());
        return elderRepository.save(elder);
    }

    @Override
    public Elder getElderById(Long id) {
        return  elderRepository.findById(id).get();
    }
}
