package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.UpdateSalaryDTO;
import elderlysitter.capstone.dto.request.ChangePasswordDTO;
import elderlysitter.capstone.dto.request.UpdateSalaryRequestDTO;
import elderlysitter.capstone.dto.request.UpdateSitterRequestDTO;
import elderlysitter.capstone.dto.response.*;
import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.entities.Rating;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.RatingRepository;
import elderlysitter.capstone.repository.SitterServiceRepository;
import elderlysitter.capstone.repository.UserRepository;
import elderlysitter.capstone.services.SitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SitterServiceImpl implements SitterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private SitterServiceRepository sitterServiceRepository;


    @Override
    public List<SittersResponseDTO> getAllSitter() {
        List<SittersResponseDTO> sittersResponseDTOList = new ArrayList<>();
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
                    .fullName(sitter.getFullName())
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
                    .fullName(sitter.getFullName())
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
            User sitter = userRepository.findById(id).get();
            SitterProfile sitterProfile = sitter.getSitterProfile();
            List<elderlysitter.capstone.entities.SitterService> sitterServices = sitter.getSitterProfile().getSitterService();
            List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
            List<CertificatesResponseDTO> certificatesResponseDTOS = new ArrayList<>();
            List<CertificateSitter> certificateSitters = sitter.getSitterProfile().getCertificateSitters();
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
                        .duration(sitterService.getService().getDuration())
                        .exp(sitterService.getExp())
                        .build();
                total = total.add(sitterService.getPrice());
                count = count.add(BigDecimal.valueOf(1));
                sitterServicesResponseDTOS.add(sitterServicesResponseDTO);
            }
            sitterResponseDTO = SitterResponseDTO.builder()
                    .id(sitter.getId())
                    .fullName(sitter.getFullName())
                    .phone(sitter.getPhone())
                    .address(sitter.getAddress())
                    .dob(sitter.getDob())
                    .email(sitter.getEmail())
                    .idNumber(sitterProfile.getIdNumber())
                    .description(sitterProfile.getDescription())
                    .avgPrice(total.divide(count))
                    .gender(sitter.getGender())
                    .certificatesResponseDTOS(certificatesResponseDTOS)
                    .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                    .avatarUrl(sitter.getAvatarImgUrl())
                    .ratingStar(averageStarOfSitter(sitter.getEmail()))
                    .frontIdImgUrl(sitter.getFrontIdImgUrl())
                    .backIdImgUrl(sitter.getBackIdImgUrl())
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
            User sitter = userRepository.findUserByEmail(email);
            SitterProfile sitterProfile = sitter.getSitterProfile();
            List<elderlysitter.capstone.entities.SitterService> sitterServices = sitter.getSitterProfile().getSitterService();
            List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
            List<CertificatesResponseDTO> certificatesResponseDTOS = new ArrayList<>();
            List<CertificateSitter> certificateSitters = sitter.getSitterProfile().getCertificateSitters();
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
                        .duration(sitterService.getService().getDuration())
                        .exp(sitterService.getExp())
                        .build();
                total = total.add(sitterService.getPrice());
                count = count.add(BigDecimal.valueOf(1));
                sitterServicesResponseDTOS.add(sitterServicesResponseDTO);
            }
            sitterResponseDTO = SitterResponseDTO.builder()
                    .id(sitter.getId())
                    .fullName(sitter.getFullName())
                    .phone(sitter.getPhone())
                    .address(sitter.getAddress())
                    .dob(sitter.getDob())
                    .email(sitter.getEmail())
                    .idNumber(sitterProfile.getIdNumber())
                    .description(sitterProfile.getDescription())
                    .avgPrice(total.divide(count))
                    .gender(sitter.getGender())
                    .ratingStar(averageStarOfSitter(sitter.getEmail()))
                    .certificatesResponseDTOS(certificatesResponseDTOS)
                    .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                    .avatarUrl(sitter.getAvatarImgUrl())
                    .frontIdImgUrl(sitter.getFrontIdImgUrl())
                    .backIdImgUrl(sitter.getBackIdImgUrl())
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
                    .fullName(sitter.getFullName())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sitterDTO;
    }

    @Override
    public SitterDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        SitterDTO sitterDTO = null;
        try {
            User sitter = userRepository.findUserByEmail(changePasswordDTO.getEmail());
            Boolean check = passwordEncoder.matches(changePasswordDTO.getOldPassword(), sitter.getPassword());
            if (check == true) {
                sitter.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                sitter = userRepository.save(sitter);
                sitterDTO = SitterDTO.builder()
                        .id(sitter.getId())
                        .avatarImgUrl(sitter.getAvatarImgUrl())
                        .dob(sitter.getDob())
                        .email(sitter.getEmail())
                        .phone(sitter.getPhone())
                        .address(sitter.getAddress())
                        .gender(sitter.getGender())
                        .fullName(sitter.getFullName())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sitterDTO;
    }

    @Override
    public UpdateSalaryResponseDTO updateSalary(UpdateSalaryRequestDTO updateSalaryRequestDTO) {
        UpdateSalaryResponseDTO updateSalaryResponseDTO = null;
        try {
            List<UpdateSalaryDTO> updateSalaryDTOS = updateSalaryRequestDTO.getUpdateSalaryDTOS();
            List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
            for (UpdateSalaryDTO updateSalaryDTO: updateSalaryDTOS
                 ) {
                elderlysitter.capstone.entities.SitterService sitterService= sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(updateSalaryResponseDTO.getSitterEmail(),updateSalaryDTO.getServiceId());
                sitterService.setNewPrice(updateSalaryDTO.getNewPrice());
                sitterService.setStatus(StatusCode.CHANGE.toString());
                sitterServiceRepository.save(sitterService);
                SitterServicesResponseDTO sitterServicesResponseDTO = SitterServicesResponseDTO.builder()
                        .id(sitterService.getId())
                        .name(sitterService.getService().getName())
                        .newPrice(sitterService.getNewPrice())
                        .duration(sitterService.getService().getDuration())
                        .exp(sitterService.getExp())
                        .build();
                sitterServicesResponseDTOS.add(sitterServicesResponseDTO);
                updateSalaryResponseDTO = UpdateSalaryResponseDTO.builder()
                        .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                        .build();
            }
            updateSalaryResponseDTO.setSitterEmail(updateSalaryResponseDTO.getSitterEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
        return updateSalaryResponseDTO;
    }


    @Override
    public UpdateSalaryResponseDTO approveUpSalary(Long sitterId) {
       UpdateSalaryResponseDTO updateSalaryResponseDTO = null;
        List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
       try{
           List<elderlysitter.capstone.entities.SitterService> sitterServices = sitterServiceRepository.findAllBySitterProfile_User_Id(sitterId);
           for (elderlysitter.capstone.entities.SitterService sitterService: sitterServices
                ) {
               elderlysitter.capstone.entities.SitterService newSitterService = sitterServiceRepository.findById(sitterService.getId()).get();
               newSitterService.setPrice(sitterService.getNewPrice());
               newSitterService.setStatus(StatusCode.ACTIVATE.toString());
               sitterServiceRepository.save(newSitterService);
               SitterServicesResponseDTO sitterServicesResponseDTO = SitterServicesResponseDTO.builder()
                       .id(sitterService.getId())
                       .name(sitterService.getService().getName())
                       .newPrice(sitterService.getNewPrice())
                       .duration(sitterService.getService().getDuration())
                       .exp(sitterService.getExp())
                       .build();
               sitterServicesResponseDTOS.add(sitterServicesResponseDTO);

           }
           updateSalaryResponseDTO = UpdateSalaryResponseDTO.builder()
                   .sitterId(sitterId)
                   .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                   .build();
       }catch (Exception e){
           e.printStackTrace();
       }
       return updateSalaryResponseDTO;
    }


    @Override
    public UpdateSalaryResponseDTO rejectUpSalary(Long sitterId) {
        UpdateSalaryResponseDTO updateSalaryResponseDTO = null;
        List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
        try{
            List<elderlysitter.capstone.entities.SitterService> sitterServices = sitterServiceRepository.findAllBySitterProfile_User_Id(sitterId);
            for (elderlysitter.capstone.entities.SitterService sitterService: sitterServices
            ) {
                elderlysitter.capstone.entities.SitterService newSitterService = sitterServiceRepository.findById(sitterService.getId()).get();
                newSitterService.setStatus(StatusCode.ACTIVATE.toString());
                sitterServiceRepository.save(newSitterService);
                SitterServicesResponseDTO sitterServicesResponseDTO = SitterServicesResponseDTO.builder()
                        .id(sitterService.getId())
                        .name(sitterService.getService().getName())
                        .newPrice(sitterService.getNewPrice())
                        .duration(sitterService.getService().getDuration())
                        .exp(sitterService.getExp())
                        .build();
                sitterServicesResponseDTOS.add(sitterServicesResponseDTO);

            }
            updateSalaryResponseDTO = UpdateSalaryResponseDTO.builder()
                    .sitterId(sitterId)
                    .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return updateSalaryResponseDTO;
    }

    @Override
    public List<UpdateSalaryResponseDTO> getAllFormUpdateSalary() {
        List<UpdateSalaryResponseDTO> updateSalaryResponseDTOS = new ArrayList<>();
        try {
            Set<String> names = new HashSet<>();
            List<elderlysitter.capstone.entities.SitterService> sitterServices = sitterServiceRepository.findAllByStatus(StatusCode.CHANGE.toString());
            for (elderlysitter.capstone.entities.SitterService sitterService: sitterServices
                 ) {
                    String email = sitterService.getSitterProfile().getUser().getEmail();
                    names.add(email);
            }
            for (String email: names
                 ) {
                sitterServices = sitterServiceRepository.findAllBySitterProfile_User_Email(email);
                List<SitterServicesResponseDTO> sitterServicesResponseDTOS = new ArrayList<>();
                Long sitterId = 0L;
                for (elderlysitter.capstone.entities.SitterService sitterService: sitterServices
                     ) {
                    SitterServicesResponseDTO sitterServicesResponseDTO = SitterServicesResponseDTO.builder()
                            .id(sitterService.getId())
                            .name(sitterService.getService().getName())
                            .newPrice(sitterService.getNewPrice())
                            .duration(sitterService.getService().getDuration())
                            .exp(sitterService.getExp())
                            .build();
                    sitterServicesResponseDTOS.add(sitterServicesResponseDTO);
                    sitterId = sitterService.getSitterProfile().getId();
                }
                UpdateSalaryResponseDTO updateSalaryResponseDTO = UpdateSalaryResponseDTO.builder()
                        .sitterId(sitterId)
                        .sitterEmail(email)
                        .sitterServicesResponseDTOS(sitterServicesResponseDTOS)
                        .build();
                updateSalaryResponseDTOS.add(updateSalaryResponseDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return updateSalaryResponseDTOS;
    }

    public Float averageStarOfSitter(String email) {
        Float star = 0F;
        try {
            List<Rating> ratings = ratingRepository.findAllBySitter_Email(email);
            if(!ratings.isEmpty()) {
                for (Rating rating : ratings) {
                    star = star + rating.getStar();
                }

                star = star / ratings.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return star;

    }

}
