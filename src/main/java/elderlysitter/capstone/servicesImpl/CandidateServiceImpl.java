package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.EmailDTO;
import elderlysitter.capstone.dto.request.AddCandidateRequestDTO;
import elderlysitter.capstone.dto.request.AddCertificateRequestDTO;
import elderlysitter.capstone.dto.request.AddSitterServiceRequestDTO;
import elderlysitter.capstone.dto.response.*;
import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.entities.SitterProfile;
import elderlysitter.capstone.entities.SitterService;
import elderlysitter.capstone.entities.User;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.*;
import elderlysitter.capstone.services.CandidateService;
import elderlysitter.capstone.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SitterProfileRepository sitterProfileRepository;

    @Autowired
    private SitterServiceRepository sitterServiceRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CertificateSitterRepository certificateSitterRepository;

    @Override
    public Boolean duplicateCandidate(AddCandidateRequestDTO addCandidateRequestDTO) {
        Boolean check = false;
        try {
            User candidate = userRepository.findUserByEmail(addCandidateRequestDTO.getEmail());
            candidate.setFullName(addCandidateRequestDTO.getFullName());
            candidate.setPhone(addCandidateRequestDTO.getPhone());
            candidate.setStatus(StatusCode.NEW.toString());
            candidate.setGender(addCandidateRequestDTO.getGender());
            candidate.setAddress(addCandidateRequestDTO.getAddress());
            candidate.setCreateDate(LocalDate.now());
            candidate.setAvatarImgUrl(addCandidateRequestDTO.getAvatarImgUrl());
            candidate.setBackIdImgUrl(addCandidateRequestDTO.getBackIdImgUrl());
            candidate.setFrontIdImgUrl(addCandidateRequestDTO.getFrontIdImgUrl());
            candidate.setDob(addCandidateRequestDTO.getDob());
            candidate = userRepository.save(candidate);

            SitterProfile candidateProfile = sitterProfileRepository.findByUser_Email(candidate.getEmail());
            candidateProfile.setUser(candidate);
            candidateProfile.setIdNumber(addCandidateRequestDTO.getIdNumber());
            candidateProfile.setDescription(addCandidateRequestDTO.getDescription());
            sitterProfileRepository.save(candidateProfile);


            List<AddSitterServiceRequestDTO> addSitterServiceRequestDTOList = addCandidateRequestDTO.getAddSitterServiceRequestDTOList();
            List<SitterService> sitterServices = sitterServiceRepository.findAllBySitterProfile_User_Email(candidate.getEmail());
            for (AddSitterServiceRequestDTO addSitterServiceRequestDTO : addSitterServiceRequestDTOList) {
                int count = 0;
                for (SitterService sitterService: sitterServices
                     ) {
                    if(sitterService.getService().getId() == addSitterServiceRequestDTO.getId()) {
                        sitterService.setPrice(addSitterServiceRequestDTO.getServicePrice());
                        sitterService.setExp(addSitterServiceRequestDTO.getExp());
                        sitterServiceRepository.save(sitterService);
                        count++;
                    }
                }
                if(count == 0) {
                    SitterService sitterService = SitterService.builder()
                            .service(serviceRepository.findById(addSitterServiceRequestDTO.getId()).get())
                            .exp(addSitterServiceRequestDTO.getExp())
                            .price(addSitterServiceRequestDTO.getServicePrice())
                            .sitterProfile(candidateProfile)
                            .status(StatusCode.ACTIVATE.toString())
                            .build();
                    sitterServiceRepository.save(sitterService);
                }
            }

            List<AddCertificateRequestDTO> addCertificateRequestDTOList = addCandidateRequestDTO.getAddCertificateRequestDTOS();
            if (addCertificateRequestDTOList != null) {
                for (AddCertificateRequestDTO addCertificateRequestDTO : addCertificateRequestDTOList) {
                    CertificateSitter certificateSitter = CertificateSitter.builder()
                            .sitterProfile(candidateProfile)
                            .url(addCertificateRequestDTO.getUrl())
                            .name(addCertificateRequestDTO.getName())
                            .build();
                    certificateSitterRepository.save(certificateSitter);
                }
            }
            check = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public CandidateResponseCommonDTO addCandidate(AddCandidateRequestDTO addCandidateRequestDTO) {
        CandidateResponseCommonDTO candidateResponseCommonDTO = null;
        try {
            User candidate = User.builder()
                    .role(roleRepository.findByName("CANDIDATE"))
                    .fullName(addCandidateRequestDTO.getFullName())
                    .dob(addCandidateRequestDTO.getDob())
                    .address(addCandidateRequestDTO.getAddress())
                    .gender(addCandidateRequestDTO.getGender())
                    .phone(addCandidateRequestDTO.getPhone())
                    .createDate(LocalDate.now())
                    .status(StatusCode.NEW.toString())
                    .frontIdImgUrl(addCandidateRequestDTO.getFrontIdImgUrl())
                    .backIdImgUrl(addCandidateRequestDTO.getBackIdImgUrl())
                    .avatarImgUrl(addCandidateRequestDTO.getAvatarImgUrl())
                    .email(addCandidateRequestDTO.getEmail())
                    .build();
            candidate = userRepository.save(candidate);
            SitterProfile candidateProfile = SitterProfile.builder()
                    .user(candidate)
                    .idNumber(addCandidateRequestDTO.getIdNumber())
                    .description(addCandidateRequestDTO.getDescription())
                    .build();
            candidateProfile = sitterProfileRepository.save(candidateProfile);
            List<AddSitterServiceRequestDTO> addSitterServiceRequestDTOList = addCandidateRequestDTO.getAddSitterServiceRequestDTOList();
            for (AddSitterServiceRequestDTO addSitterServiceRequestDTO : addSitterServiceRequestDTOList) {
                SitterService sitterService = SitterService.builder()
                        .service(serviceRepository.findById(addSitterServiceRequestDTO.getId()).get())
                        .exp(addSitterServiceRequestDTO.getExp())
                        .price(addSitterServiceRequestDTO.getServicePrice())
                        .sitterProfile(candidateProfile)
                        .status(StatusCode.ACTIVATE.toString())
                        .build();
                sitterServiceRepository.save(sitterService);
            }
            List<AddCertificateRequestDTO> addCertificateRequestDTOList = addCandidateRequestDTO.getAddCertificateRequestDTOS();
            if (addCertificateRequestDTOList != null) {
                for (AddCertificateRequestDTO addCertificateRequestDTO : addCertificateRequestDTOList) {
                    CertificateSitter certificateSitter = CertificateSitter.builder()
                            .sitterProfile(candidateProfile)
                            .url(addCertificateRequestDTO.getUrl())
                            .name(addCertificateRequestDTO.getName())
                            .build();
                    certificateSitterRepository.save(certificateSitter);
                }
            }
            candidateResponseCommonDTO = convertToDTO(candidate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidateResponseCommonDTO;
    }

    @Override
    public CandidateResponseCommonDTO acceptCandidate(String email) {
        CandidateResponseCommonDTO addCandidateResponseDTO = null;
        try {
            String password = randomPassword() + "ELS1";
            User candidate = userRepository.findUserByEmail(email);
            if (candidate == null) {
                return null;
            }
            candidate.setRole(roleRepository.findByName("SITTER"));
            candidate.setStatus(StatusCode.ACTIVATE.toString());
            candidate.setPassword(passwordEncoder.encode(password));
            EmailDTO emailDetails = EmailDTO.builder()
                    .email(email)
                    .subject("Th??ng b??o ????ng k?? th??nh c??ng t??i kho???n c???a ELS")
                    .massage("Xin ch??o " + candidate.getFullName() + ",\n" +
                            "\n" +
                            "Ch??ng t??i xin ch??c m???ng b???n ???? tr??? th??nh nh??n vi??n ch??m s??c c???a Elderly Sitter. Sau khi ?????c b???n ????ng k?? c???a b???n, ch??ng t??i r???t ???n t?????ng v??? k?? n??ng chuy??n m??n c??ng nh?? kinh nghi???m l??m vi???c c???a anh/ch???. \n" +
                            "\n" +
                            "D?????i ????y l?? username/ password c???a anh/ch???\n" +
                            "\n" +
                            "Username: " + candidate.getEmail() + "\n" +
                            "Password: " + password + "\n" +
                            "\n" +
                            "M???t l???n n???a, xin ch??c m???ng v?? ch??o m???ng anh/ch??? ?????n v???i ELS!\n" +
                            "\n" +
                            "Tr??n tr???ng,\n" +
                            "\n" +
                            "Ph??ng Qu???n l?? Nh??n s???.\n" +
                            "(????y l?? email ???????c g???i t??? ?????ng, Qu?? kh??ch vui l??ng kh??ng h???i ????p theo ?????a ch??? email n??y.)")
                    .build();

            emailService.sendSimpleMail(emailDetails);
            candidate = userRepository.save(candidate);
            addCandidateResponseDTO = convertToDTO(candidate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addCandidateResponseDTO;

    }

    @Override
    public CandidateResponseCommonDTO rejectCandidate(String email) {
        CandidateResponseCommonDTO addCandidateResponseDTO = null;
        try {
            User candidate = userRepository.findUserByEmail(email);
            String fullName = candidate.getFullName();
            candidate.setStatus(StatusCode.REJECTED.toString());
            userRepository.save(candidate);

            EmailDTO emailDetails = EmailDTO.builder()
                    .email(email)
                    .subject("Th??ng b??o v??? k???t qu??? ????ng k?? t??i kho???n ELS")
                    .massage("Xin ch??o " + fullName + ",\n" +
                            "\n" +
                            "Ch??ng t??i ???? ?????c v?? r???t ???n t?????ng v???i k?? n??ng chuy??n m??n v?? kinh nghi???m l??m vi???c m?? b???n ???? ghi trong form ????ng k??. \n" +
                            "Nh??ng ch??ng t??i r???t ti???c khi ph???i th??ng b??o r???ng b???n kh??ng ph?? h???p v???i ti??u ch?? v?? m???c ti??u c???a ELS.\n" +
                            "\n" +
                            "Mong s???m ???????c h???p t??c c??ng b???n trong t????ng lai.\n" +
                            "\n" +
                            "Tr??n tr???ng,\n" +
                            "\n" +
                            "Ph??ng Qu???n l?? Nh??n s???.\n" +
                            "(????y l?? email ???????c g???i t??? ?????ng, Qu?? kh??ch vui l??ng kh??ng h???i ????p theo ?????a ch??? email n??y.)")
                    .build();
            emailService.sendSimpleMail(emailDetails);
            addCandidateResponseDTO = convertToDTO(candidate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addCandidateResponseDTO;

    }

    @Override
    public List<CandidatesResponseDTO> getAllCandidate() {
        List<CandidatesResponseDTO> candidatesResponseDTOList = new ArrayList<>();
        try {
            List<User> candidates = userRepository.findAllByRole_Name("CANDIDATE");
            for (User candidate : candidates) {
                BigDecimal total = BigDecimal.valueOf(0);
                BigDecimal count = BigDecimal.valueOf(0);
                List<SitterService> sitterServices = candidate.getSitterProfile().getSitterService();
                for (SitterService sitterService : sitterServices) {
                    count = count.add(BigDecimal.valueOf(1));
                    total = total.add(sitterService.getPrice());
                }
                CandidatesResponseDTO candidatesResponseDTO = CandidatesResponseDTO.builder()
                        .id(candidate.getId())
                        .name(candidate.getFullName())
                        .email(candidate.getEmail())
                        .address(candidate.getEmail())
                        .avgPrice(total.divide(count))
                        .status(candidate.getStatus())
                        .build();
                candidatesResponseDTOList.add(candidatesResponseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidatesResponseDTOList;
    }

    @Override
    public CandidateResponseDTO getById(Long id) {
        CandidateResponseDTO candidateResponseDTO = null;
        try {
            User candidate = userRepository.findById(id).get();
            SitterProfile candidateProfile = candidate.getSitterProfile();
            List<SitterService> candidateServices = candidate.getSitterProfile().getSitterService();
            List<CandidateServicesResponseDTO> candidateServicesResponseDTOS = new ArrayList<>();
            List<CertificatesResponseDTO> certificatesResponseDTOS = new ArrayList<>();
            List<CertificateSitter> certificateCandidates = candidate.getSitterProfile().getCertificateSitters();
            for (CertificateSitter certificateCandidate : certificateCandidates) {
                CertificatesResponseDTO certificatesResponseDTO = CertificatesResponseDTO.builder()
                        .name(certificateCandidate.getName())
                        .url(certificateCandidate.getUrl())
                        .build();
                certificatesResponseDTOS.add(certificatesResponseDTO);
            }
            BigDecimal count = BigDecimal.valueOf(0);
            BigDecimal total = BigDecimal.valueOf(0);
            for (SitterService candidateService : candidateServices) {
                CandidateServicesResponseDTO candidateServicesResponseDTO = CandidateServicesResponseDTO.builder()
                        .name(candidateService.getService().getName())
                        .price(candidateService.getPrice())
                        .exp(candidateService.getExp())
                        .build();
                total = total.add(candidateService.getPrice());
                count = count.add(BigDecimal.valueOf(1));
                candidateServicesResponseDTOS.add(candidateServicesResponseDTO);
            }
            candidateResponseDTO = CandidateResponseDTO.builder()
                    .id(candidate.getId())
                    .fullName(candidate.getFullName())
                    .phone(candidate.getPhone())
                    .address(candidate.getAddress())
                    .dob(candidate.getDob())
                    .email(candidate.getEmail())
                    .idNumber(candidateProfile.getIdNumber())
                    .description(candidateProfile.getDescription())
                    .avgPrice(total.divide(count))
                    .certificatesResponseDTOS(certificatesResponseDTOS)
                    .candidateServicesResponseDTOS(candidateServicesResponseDTOS)
                    .avatarUrl(candidate.getAvatarImgUrl())
                    .frontIdImgUrl(candidate.getFrontIdImgUrl())
                    .backIdImgUrl(candidate.getBackIdImgUrl())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidateResponseDTO;
    }

    private CandidateResponseCommonDTO convertToDTO(User candidate) {
        CandidateResponseCommonDTO addCandidateResponseDTO = null;
        try {
            addCandidateResponseDTO = CandidateResponseCommonDTO.builder()
                    .fullName(candidate.getFullName())
                    .address(candidate.getAddress())
                    .email(candidate.getEmail())
                    .phone(candidate.getPhone())
                    .gender(candidate.getGender())
                    .dob(candidate.getDob())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addCandidateResponseDTO;

    }

    public String randomPassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;

    }
}
