package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.ElderService;
import elderlysitter.capstone.Services.UserService;
import elderlysitter.capstone.dto.ElderDTO;
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
    public List<Elder> getAllElder() {
        List<Elder> elders = elderRepository.findAll();
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
}
