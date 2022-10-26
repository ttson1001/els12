package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.SitterServiceService;
import elderlysitter.capstone.dto.CertificateDTO;
import elderlysitter.capstone.dto.SitterServiceDTO;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.dto.SitterResponseDTO;
import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.CertificateSitterRepository;
import elderlysitter.capstone.repository.SitterProfileRepository;
import elderlysitter.capstone.repository.SitterServiceRepository;
import elderlysitter.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SitterServiceImpl implements SitterServiceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SitterProfileRepository sitterProfileRepository;

    @Autowired
    CertificateSitterRepository certificateSitterRepository;

    @Autowired
    SitterServiceRepository sitterServiceRepository;

    @Override
    public SitterResponseDTO getSitterByEmail(String email) {
        BigDecimal total = BigDecimal.valueOf(0);
        BigDecimal count = BigDecimal.valueOf(0);
        User sitter = userRepository.findUserByEmail(email);
        SitterProfile sitterProfile = sitterProfileRepository.findByUser_Email(email);
        List<CertificateSitter> certificateSitter = certificateSitterRepository.findAllBySitterProfile_User_Email(email);
        List<SitterService> sitterServices = sitterServiceRepository.findAllBySitterProfile_User_Email(email);

        List<CertificateDTO> certificateDTOList =  new ArrayList<>();

        List<SitterServiceDTO> sitterServiceDTOList = new ArrayList<>();

        for (CertificateSitter item : certificateSitter
        ) {
            CertificateDTO certificateDTO = CertificateDTO.builder()
                    .url(item.getUrl())
                    .build();
            certificateDTOList.add(certificateDTO);
        }


        for (SitterService item : sitterServices
        ) {
            total = total.add(item.getPrice());
            count = count.add(BigDecimal.valueOf(1));
            SitterServiceDTO sitterServiceDTO = SitterServiceDTO.builder()
                    .serviceName(item.getService().getName())
                    .servicePrice(item.getPrice())
                    .build();
            sitterServiceDTOList.add(sitterServiceDTO);
        }

        SitterResponseDTO responseDTO = SitterResponseDTO.builder()
                .fullName(sitter.getFullName())
                .dob(sitter.getDob())
                .phone(sitter.getPhone())
                .certificateDTOS(certificateDTOList)
                .gender(sitter.getGender())
                .email(sitter.getEmail())
                .ratingStar(4.0F)
                .avgPrice(total.divide(count))
                .sitterServices(sitterServiceDTOList)
                .idNumber(sitterProfile.getIdNumber())
                .address(sitter.getAddress())
                .build();

        return  responseDTO;

    }
}
