package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.CandidateService;
import elderlysitter.capstone.Services.EmailService;
import elderlysitter.capstone.dto.*;
import elderlysitter.capstone.entities.*;
import elderlysitter.capstone.repository.*;
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
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UserImgRepository userImgRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    SitterServiceRepository sitterServiceRepository;

    @Autowired
    CertificateSitterRepository certificateSitterRepository;

    @Autowired
    SitterProfileRepository sitterProfileRepository;

    @Override
    public List<CandidateResponseDTO> getAllCandidate() {
        List<User> users = userRepository.findAllByRole_NameAndStatus("CANDIDATE", "APPLY");
        List<CandidateResponseDTO> list = new ArrayList<>();
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


                CandidateResponseDTO dto = CandidateResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getFullName())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .avgPrice(total.divide(count))
                        .build();
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User acceptCandidate(String email) {
        String password = randomPassword() + "ELS1";
        User sitter = userRepository.findUserByEmail(email);
        if (sitter == null) {
            return null;
        }
        sitter.setRole(roleRepository.findByName("SITTER"));
        sitter.setStatus("ACTIVE");
        sitter.setPassword(passwordEncoder.encode(password));
        EmailDTO emailDetails = EmailDTO.builder()
                .email(email)
                .subject("Thông báo đăng ký thành công tài khoản của ELS")
                .massage("Xin chào " + sitter.getFullName() + ",\n" +
                        "\n" +
                        "Chúng tôi xin chúc mừng bạn đã trở thành nhân viên chăm sóc của Elderly Sitter. Sau khi đọc bản đăng ký của bạn, chúng tôi rất ấn tượng về kĩ năng chuyên môn cũng như kinh nghiệm làm việc của anh/chị. \n" +
                        "\n" +
                        "Dưới đây là username/ password của anh/chị\n" +
                        "\n" +
                        "Username: " + sitter.getEmail() + "\n" +
                        "Password: " + password + "\n" +
                        "\n" +
                        "Một lần nữa, xin chúc mừng và chào mừng anh/chị đến với ELS!\n" +
                        "\n" +
                        "Trân trọng,\n" +
                        "\n" +
                        "Phòng Quản lý Nhân sự.\n" +
                        "(Đây là email được gửi tự động, Quý khách vui lòng không hồi đáp theo địa chỉ email này.)")
                .build();
        emailService.sendSimpleMail(emailDetails);
        return userRepository.save(sitter);
    }

    @Override
    public User rejectCandidate(String email) {
        try {
//            List<SitterService> sitterServices = sitterServiceRepository.findAllBySitterProfile_User_Email(email);
//            for (SitterService item : sitterServices) {
//                sitterServiceRepository.delete(item);
//            }
//            List<CertificateSitter> certificateSitters = certificateSitterRepository.findAllBySitterProfile_User_Email(email);
//            for (CertificateSitter item : certificateSitters) {
//                certificateSitterRepository.delete(item);
//            }
//
//            SitterProfile sitterProfile = sitterProfileRepository.findByUser_Email(email);
//            sitterProfileRepository.delete(sitterProfile);
//
//            User candidate = userRepository.findUserByEmail(email);
//            String fullName = candidate.getFullName();
//            userRepository.delete(candidate);
            User candidate = userRepository.findUserByEmail(email);
            String fullName = candidate.getFullName();
            candidate.setStatus("REJECT");
            userRepository.save(candidate);

            EmailDTO emailDetails = EmailDTO.builder()
                    .email(email)
                    .subject("Thông báo về kết quả đăng ký tài khoản ELS")
                    .massage("Xin chào " + fullName + ",\n" +
                            "\n" +
                            "Chúng tôi đã đọc và rất ấn tượng với kĩ năng chuyên môn và kinh nghiệm làm việc mà bạn đã ghi trong form đăng ký. \n" +
                            "Nhưng chúng tôi rất tiếc khi phải thông báo rằng bạn không phù hợp với tiêu chí và mục tiêu của ELS.\n" +
                            "\n" +
                            "Mong sớm được hợp tác cùng bạn trong tương lai.\n" +
                            "\n" +
                            "Trân trọng,\n" +
                            "\n" +
                            "Phòng Quản lý Nhân sự.\n" +
                            "(Đây là email được gửi tự động, Quý khách vui lòng không hồi đáp theo địa chỉ email này.)")
                    .build();
            emailService.sendSimpleMail(emailDetails);

            return userRepository.save(candidate);
        } catch (Exception e) {
        }
        return null;
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

    @Override
    public SitterProfile getCandidateProfileByEmail(String email) {
        return sitterProfileRepository.findByUser_Email(email);
    }

    @Override
    public SitterProfile getCandidateProfileById(Long id) {
        return sitterProfileRepository.findById(id).get();
    }

    @Override
    public User addCandidate(CandidateRequestDTO candidateRequestDTO) {
        User candidate = User.builder()
                .role(roleRepository.findByName("CANDIDATE"))
                .fullName(candidateRequestDTO.getFullName())
                .dob(candidateRequestDTO.getDob())
                .address(candidateRequestDTO.getAddress())
                .gender(candidateRequestDTO.getGender())
                .phone(candidateRequestDTO.getPhone())
                .createDate(LocalDate.now())
                .status("APPLY")
                .email(candidateRequestDTO.getEmail()).build();
        User newSitter = userRepository.save(candidate);
        SitterProfile sitterProfile = SitterProfile.builder()
                .user(newSitter)
                .idNumber(candidateRequestDTO.getIdNumber())
                .description(candidateRequestDTO.getDescription())
                .build();
        SitterProfile sitterProfile1 = sitterProfileRepository.save(sitterProfile);

        List<SitterServiceRequestDTO> sitterServiceRequestDTOS = candidateRequestDTO.getSitterServiceRequestDTOS();
        for (SitterServiceRequestDTO item : sitterServiceRequestDTOS
        ) {
            SitterService sitterService = SitterService.builder()
                    .service(serviceRepository.findById(item.getId()).get())
                    .exp(item.getExp())
                    .price(item.getServicePrice())
                    .sitterProfile(sitterProfile1)
                    .build();
            sitterServiceRepository.save(sitterService);
        }


        List<CertificateDTO> list = candidateRequestDTO.getCertificateDTOS();

        for (int i = 0; i < list.size(); i++) {
            CertificateSitter certificateSitter = CertificateSitter.builder()
                    .sitterProfile(sitterProfile1)
                    .url(list.get(i).getUrl())
                    .name(list.get(i).getName())
                    .build();
            certificateSitterRepository.save(certificateSitter);
        }

        UserImg userImg = UserImg.builder()
                .avatarImgUrl(candidateRequestDTO.getUserImgDTO().getAvatarImgUrl())
                .backIdImgUrl(candidateRequestDTO.getUserImgDTO().getBackIdImgUrl())
                .fontIdImgUrl(candidateRequestDTO.getUserImgDTO().getFontIdImgUrl())
                .build();

        userImgRepository.save(userImg);

        return newSitter;
    }


}
