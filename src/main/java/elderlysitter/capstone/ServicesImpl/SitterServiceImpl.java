package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.SitterServiceService;
import elderlysitter.capstone.dto.*;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.repository.*;
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

    @Autowired
    RoleRepository roleRepository;


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

    @Override
    public List<SitterGetAllResponseDTO> getAllSitter() {
        List<User> users = userRepository.findAllByRole(roleRepository.findByName("SITTER"));
        List<SitterGetAllResponseDTO> list = new ArrayList<>();
        try {


            for (User user : users) {
                BigDecimal total = BigDecimal.valueOf(0);
                BigDecimal count = BigDecimal.valueOf(0);
                List<SitterService> sitterServices = user.getSitterProfile().getSitterService();
                for (SitterService sitterService : sitterServices
                ) {
                    count = count.add(BigDecimal.valueOf(1));
                    total = total.add(sitterService.getPrice());
                }
                if(count == BigDecimal.valueOf(0)){
                         total = BigDecimal.valueOf(0);
                }


                SitterGetAllResponseDTO dto = SitterGetAllResponseDTO.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .ratingStar(5F)
                        .gender(user.getGender())
                        .dob(user.getDob())
                        .crateDate(user.getCreateDate())
                        .avgPrice(total.divide(count))
                        .build();
                list.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


}
