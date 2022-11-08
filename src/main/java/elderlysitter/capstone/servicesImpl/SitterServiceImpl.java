package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.UpdateSitterRequestDTO;
import elderlysitter.capstone.dto.response.*;
import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.SitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SitterServiceImpl implements SitterService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<SittersResponseDTO> getAllSitter() {
        List<SittersResponseDTO> sittersResponseDTOList = null;
        try {
            List<User> sitters = userRepository.findAllByRole_Name("SITTER");
            for (User sitter : sitters) {
                BigDecimal count = BigDecimal.valueOf(0);
                BigDecimal total = BigDecimal.valueOf(0);
                List<elderlysitter.capstone.entities.SitterService> sitterServices = sitter.getSitterProfile().getSitterService();
                for (elderlysitter.capstone.entities.SitterService sitterService : sitterServices) {
                    count = count.add(BigDecimal.valueOf(1));
                    total = total.add(sitterService.getPrice());
                }

                SittersResponseDTO sittersResponseDTO = SittersResponseDTO.builder()
                        .fullName(sitter.getFullName())
                        .phone(sitter.getPhone())
                        .status(sitter.getStatus())
                        .createDate(sitter.getCreateDate())
                        .gender(sitter.getGender())
                        .avgPrice(total.divide(count))
                        .address(sitter.getAddress())
                        .id(sitter.getId())
                        .build();
                sittersResponseDTOList.add(sittersResponseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sittersResponseDTOList;
    }

    @Override
    public SitterDTO activate(Long id) {
        SitterDTO sitterDTO = null;
        try {
            User sitter = userRepository.findById(id).get();
            sitter.setStatus(StatusCode.ACTIVATE.toString());
            sitter = userRepository.save(sitter);
            sitterDTO = SitterDTO.builder()
                    .id(sitter.getId())
                    .avatarImgUrl(sitter.getAvatarImgUrl())
                    .dob(sitter.getDob())
                    .email(sitter.getEmail())
                    .phone(sitter.getPhone())
                    .address(sitter.getAddress())
                    .gender(sitter.getGender())
                    .fullName(sitter.getGender())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sitterDTO;
    }

    @Override
    public SitterDTO deactivate(Long id) {
        SitterDTO sitterDTO = null;
        try {
            User sitter = userRepository.findById(id).get();
            sitter.setStatus(StatusCode.DEACTIVATE.toString());
            sitter = userRepository.save(sitter);
            sitterDTO = SitterDTO.builder()
                    .id(sitter.getId())
                    .avatarImgUrl(sitter.getAvatarImgUrl())
                    .dob(sitter.getDob())
                    .email(sitter.getEmail())
                    .phone(sitter.getPhone())
                    .address(sitter.getAddress())
                    .gender(sitter.getGender())
                    .fullName(sitter.getGender())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sitterDTO;
    }

    @Override
    public SitterResponseDTO getSitterById(Long id) {
        SitterResponseDTO sitterResponseDTO = null;
        try {
            User candidate = userRepository.findById(id).get();
            SitterProfile sitterProfile = candidate.getSitterProfile();
            List<elderlysitter.capstone.entities.SitterService> sitterServices = candidate.getSitterProfile().getSitterService();
            List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
            List<CertificatesResponseDTO> certificatesResponseDTOS = new ArrayList<>();
            List<CertificateSitter> certificateSitters = candidate.getSitterProfile().getCertificateSitters();
            for (CertificateSitter certificateSitter : certificateSitters) {
                CertificatesResponseDTO certificatesResponseDTO = CertificatesResponseDTO.builder()
                        .name(certificateSitter.getName())
                        .url(certificateSitter.getUrl())
                        .build();
                certificatesResponseDTOS.add(certificatesResponseDTO);
            }
            BigDecimal count = BigDecimal.valueOf(0);
            BigDecimal total = BigDecimal.valueOf(0);
            for (elderlysitter.capstone.entities.SitterService sitterService : sitterServices) {
                SitterServicesResponseDTO sitterServicesResponseDTO = SitterServicesResponseDTO.builder()
                        .name(sitterService.getService().getName())
                        .price(sitterService.getPrice())
                        .exp(sitterService.getExp())
                        .build();
                total = total.add(sitterService.getPrice());
                count = count.add(BigDecimal.valueOf(1));
                sitterServicesResponseDTOS.add(sitterServicesResponseDTO);
            }
            sitterResponseDTO = SitterResponseDTO.builder()
                    .id(candidate.getId())
                    .fullName(candidate.getFullName())
                    .phone(candidate.getPhone())
                    .address(candidate.getAddress())
                    .dob(candidate.getDob())
                    .email(candidate.getEmail())
                    .idNumber(sitterProfile.getIdNumber())
                    .description(sitterProfile.getDescription())
                    .avgPrice(total.divide(count))
                    .certificatesResponseDTOS(certificatesResponseDTOS)
                    .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                    .avatarUrl(candidate.getAvatarImgUrl())
                    .ratingStart(4F)
                    .frontIdImgUrl(candidate.getFrontIdImgUrl())
                    .backIdImgUrl(candidate.getBackIdImgUrl())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sitterResponseDTO;
    }

    @Override
    public SitterResponseDTO getSitterByEmail(String email) {
        SitterResponseDTO sitterResponseDTO = null;
        try {
            User candidate = userRepository.findUserByEmail(email);
            SitterProfile sitterProfile = candidate.getSitterProfile();
            List<elderlysitter.capstone.entities.SitterService> sitterServices = candidate.getSitterProfile().getSitterService();
            List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
            List<CertificatesResponseDTO> certificatesResponseDTOS = new ArrayList<>();
            List<CertificateSitter> certificateSitters = candidate.getSitterProfile().getCertificateSitters();
            for (CertificateSitter certificateSitter : certificateSitters) {
                CertificatesResponseDTO certificatesResponseDTO = CertificatesResponseDTO.builder()
                        .name(certificateSitter.getName())
                        .url(certificateSitter.getUrl())
                        .build();
                certificatesResponseDTOS.add(certificatesResponseDTO);
            }
            BigDecimal count = BigDecimal.valueOf(0);
            BigDecimal total = BigDecimal.valueOf(0);
            for (elderlysitter.capstone.entities.SitterService sitterService : sitterServices) {
                SitterServicesResponseDTO sitterServicesResponseDTO = SitterServicesResponseDTO.builder()
                        .name(sitterService.getService().getName())
                        .price(sitterService.getPrice())
                        .exp(sitterService.getExp())
                        .build();
                total = total.add(sitterService.getPrice());
                count = count.add(BigDecimal.valueOf(1));
                sitterServicesResponseDTOS.add(sitterServicesResponseDTO);
            }
            sitterResponseDTO = SitterResponseDTO.builder()
                    .id(candidate.getId())
                    .fullName(candidate.getFullName())
                    .phone(candidate.getPhone())
                    .address(candidate.getAddress())
                    .dob(candidate.getDob())
                    .email(candidate.getEmail())
                    .idNumber(sitterProfile.getIdNumber())
                    .description(sitterProfile.getDescription())
                    .avgPrice(total.divide(count))
                    .ratingStart(4F)
                    .certificatesResponseDTOS(certificatesResponseDTOS)
                    .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                    .avatarUrl(candidate.getAvatarImgUrl())
                    .frontIdImgUrl(candidate.getFrontIdImgUrl())
                    .backIdImgUrl(candidate.getBackIdImgUrl())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sitterResponseDTO;
    }

    @Override
    public SitterDTO updateSitter(UpdateSitterRequestDTO updateSitterRequestDTO) {
        SitterDTO sitterDTO = null;
        try {
            User sitter = userRepository.findUserByEmail(updateSitterRequestDTO.getEmail());
            sitter.setFullName(updateSitterRequestDTO.getFullName());
            sitter.setAvatarImgUrl(updateSitterRequestDTO.getAvatarImgUrl());
            sitter.setDob(updateSitterRequestDTO.getDob());
            sitter.setAddress(updateSitterRequestDTO.getAddress());
            sitter.setGender(updateSitterRequestDTO.getGender());
            sitter.setPhone(updateSitterRequestDTO.getPhone());
            sitter = userRepository.save(sitter);

            sitterDTO = SitterDTO.builder()
                    .id(sitter.getId())
                    .avatarImgUrl(sitter.getAvatarImgUrl())
                    .dob(sitter.getDob())
                    .email(sitter.getEmail())
                    .phone(sitter.getPhone())
                    .address(sitter.getAddress())
                    .gender(sitter.getGender())
                    .fullName(sitter.getGender())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sitterDTO;
    }
}
