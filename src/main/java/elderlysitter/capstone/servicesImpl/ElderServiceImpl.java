package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.services.ElderService;
import elderlysitter.capstone.dto.request.AddElderRequestDTO;
import elderlysitter.capstone.dto.request.UpdateElderRequestDTO;
import elderlysitter.capstone.entities.Elder;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.ElderRepository;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElderServiceImpl implements ElderService {

    @Autowired
    private ElderRepository elderRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Elder addElder(AddElderRequestDTO addElderRequestDTO) {
        Elder elder = null;
        try {
            elder = Elder.builder()
                    .name(addElderRequestDTO.getName())
                    .note(addElderRequestDTO.getNote())
                    .dob(addElderRequestDTO.getDob())
                    .gender(addElderRequestDTO.getGender())
                    .user(userRepository.findUserByEmail(addElderRequestDTO.getEmail()))
                    .isAllergy(addElderRequestDTO.getIsAllergy())
                    .healthStatus(addElderRequestDTO.getHealthStatus())
                    .status(StatusCode.ACTIVATE.toString())
                    .build();
            elder = elderRepository.save(elder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return elder;
    }

    @Override
    public Elder updateElder(UpdateElderRequestDTO updateElderRequestDTO) {
        Elder elder = null;
        try {
            elder = elderRepository.findById(updateElderRequestDTO.getId()).get();
            elder.setName(updateElderRequestDTO.getName());
            elder.setNote(updateElderRequestDTO.getNote());
            elder.setDob(updateElderRequestDTO.getDob());
            elder.setGender(updateElderRequestDTO.getGender());
            elder.setIsAllergy(updateElderRequestDTO.getIsAllergy());
            elder.setHealthStatus(updateElderRequestDTO.getHealthStatus());
            elder = elderRepository.save(elder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return elder;
    }

    @Override
    public Elder removeElder(Long id) {
        Elder elder = null;
        try {
            elder = elderRepository.findById(id).get();
            elder.setStatus(StatusCode.DEACTIVATE.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return elder;
    }

    @Override
    public List<Elder> getAllElderByCustomerEmail(String email) {
        List<Elder> elders = null;
        try {
            elders = elderRepository.findAllByUser_Email(email);
        }catch (Exception e){
            e.printStackTrace();
        }
        return elders;
    }

    @Override
    public Elder getElderById(Long id) {
        Elder elder = null;
        try {
            elder = elderRepository.findById(id).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return elder;
    }
}
