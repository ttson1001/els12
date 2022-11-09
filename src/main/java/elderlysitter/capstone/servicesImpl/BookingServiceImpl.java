package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.SitterDTO;
import elderlysitter.capstone.dto.request.AddBookingRequestDTO;
import elderlysitter.capstone.dto.request.AddBookingServiceRequestDTO;
import elderlysitter.capstone.dto.request.AddWorkingTimesRequestDTO;
import elderlysitter.capstone.dto.response.*;
import elderlysitter.capstone.entities.*;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.*;
import elderlysitter.capstone.services.BookingService;
import elderlysitter.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@org.springframework.stereotype.Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Autowired
    SitterServiceRepository sitterServiceRepository;

    @Autowired
    ElderRepository elderRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    BookingDetailRepository bookingDetailRepository;

    @Autowired
    WorkingTimeRepository workingTimeRepository;

    @Override
    public BookingDTO addBooking(AddBookingRequestDTO addBookingRequestDTO) {
        UUID uuid = UUID.randomUUID();
        BookingDTO bookingDTO = null;
        try {
            List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOS = addBookingRequestDTO.getAddWorkingTimesDTOList();
            List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS = addBookingRequestDTO.getAddBookingServiceRequestDTOS();

            User sitter = userService.randomSitter(addBookingServiceRequestDTOS, "");
            if (sitter == null) {
                return null;
            }

            BigDecimal total = BigDecimal.valueOf(0);
            BigDecimal deposit = BigDecimal.valueOf(0);

            for (AddBookingServiceRequestDTO addBookingServiceRequestDTO : addBookingServiceRequestDTOS) {
                SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(sitter.getEmail(), addBookingServiceRequestDTO.getId());
                Long commission = sitterService.getService().getCommission();
                deposit = deposit.add(sitterService.getPrice().multiply(BigDecimal.valueOf(addBookingServiceRequestDTO.getDuration()).divide(BigDecimal.valueOf(60))).multiply(BigDecimal.valueOf(commission)));
                deposit = deposit.multiply(BigDecimal.valueOf(addWorkingTimesRequestDTOS.size()));
                total = total.add(sitterService.getPrice().multiply(BigDecimal.valueOf(addBookingServiceRequestDTO.getDuration()).divide(BigDecimal.valueOf(60))));
            }

            Booking booking = Booking.builder()
                    .name(uuid.toString())
                    .address(addBookingRequestDTO.getAddress())
                    .place(addBookingRequestDTO.getPlace())
                    .description(addBookingRequestDTO.getDescription())
                    .status(StatusCode.WAITING_FOR_SITTER.toString())
                    .elder(elderRepository.findById(addBookingRequestDTO.getElderId()).get())
                    .sitter(sitter)
                    .deposit(deposit)
                    .totalPrice(total.multiply(BigDecimal.valueOf(addWorkingTimesRequestDTOS.size())))
                    .user(userRepository.findUserByEmail(addBookingRequestDTO.getEmail()))
                    .build();
            bookingRepository.save(booking);
            Booking newBooking = bookingRepository.findBookingByName(booking.getName());
            for (int i = 0; i < addBookingRequestDTO.getAddBookingServiceRequestDTOS().size(); i++) {
                Service service = serviceRepository.findById(addBookingServiceRequestDTOS.get(i).getId()).get();
                BigDecimal price = (sitter.getSitterProfile().getSitterService().get(i).getPrice().multiply(BigDecimal.valueOf(addBookingRequestDTO.getAddBookingServiceRequestDTOS().get(i).getDuration()).divide(BigDecimal.valueOf(60))));
                BookingDetail bookingDetail = BookingDetail.builder()
                        .booking(newBooking)
                        .service(service)
                        .commission(service.getCommission())
                        .duration(addBookingRequestDTO.getAddBookingServiceRequestDTOS().get(i).getDuration())
                        .price(price)
                        .build();
                bookingDetailRepository.save(bookingDetail);
            }
            for (AddWorkingTimesRequestDTO addWorkingTimesRequestDTO : addWorkingTimesRequestDTOS) {
                WorkingTime workingTime = WorkingTime.builder()
                        .booking(newBooking)
                        .localDate(addWorkingTimesRequestDTO.getLocalDate())
                        .startTime(addWorkingTimesRequestDTO.getStartTime())
                        .endTime(addWorkingTimesRequestDTO.getEndTime())
                        .build();
                workingTimeRepository.save(workingTime);
            }

            bookingDTO = convertBookingToBookingDTO(newBooking);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingDTO;
    }

    @Override
    public List<BookingsResponseDTO> getAllBooking() {
        List<BookingsResponseDTO> bookingsResponseDTOS = null;
        try {
            List<Booking> bookings = bookingRepository.findAll();
            for (Booking booking : bookings) {
                BookingsResponseDTO bookingsResponseDTO = BookingsResponseDTO.builder()
                        .id(booking.getId())
                        .name(booking.getName())
                        .sitterName(booking.getSitter().getFullName())
                        .place(booking.getPlace())
                        .deposit(booking.getDeposit())
                        .totalPrice(booking.getTotalPrice())
                        .status(booking.getStatus())
                        .build();
                bookingsResponseDTOS.add(bookingsResponseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingsResponseDTOS;
    }

    @Override
    public BookingResponseDTO getBookingById(Long id) {
        BookingResponseDTO bookingResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(id).get();
            bookingResponseDTO = convertBookingToBookingResponseDTO(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingResponseDTO;
    }

    @Override
    public List<BookingsResponseDTO> getAllBookingByStatus(String status) {
        List<BookingsResponseDTO> bookingsResponseDTOS = null;
        try {
            List<Booking> bookings = bookingRepository.findAllByStatus(status);
            for (Booking booking : bookings) {
                BookingsResponseDTO bookingsResponseDTO = BookingsResponseDTO.builder()
                        .id(booking.getId())
                        .name(booking.getName())
                        .sitterName(booking.getSitter().getFullName())
                        .place(booking.getPlace())
                        .deposit(booking.getDeposit())
                        .totalPrice(booking.getTotalPrice())
                        .status(booking.getStatus())
                        .build();
                bookingsResponseDTOS.add(bookingsResponseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingsResponseDTOS;
    }

    @Override
    public BookingDTO acceptBookingForSitter(Long id) {
        BookingDTO bookingDTO = null;
        try {
            Booking booking = bookingRepository.findById(id).get();
            booking.setStatus(StatusCode.WAITING_FOR_SITTER.toString());
            booking = bookingRepository.save(booking);
            bookingDTO = convertBookingToBookingDTO(booking);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookingDTO;
    }

    public BookingResponseDTO convertBookingToBookingResponseDTO(Booking booking) {
        BookingResponseDTO bookingResponseDTO = null;
        try {
            ElderResponseDTO elderResponseDTO = ElderResponseDTO.builder()
                    .id(booking.getElder().getId())
                    .name(booking.getElder().getName())
                    .gender(booking.getElder().getGender())
                    .dob(booking.getElder().getDob())
                    .note(booking.getElder().getNote())
                    .isAllergy(booking.getElder().getIsAllergy())
                    .build();

            SitterDTO sitterDTO = SitterDTO.builder()
                    .id(booking.getSitter().getId())
                    .fullName(booking.getSitter().getFullName())
                    .dob(booking.getSitter().getDob())
                    .gender(booking.getSitter().getGender())
                    .phone(booking.getSitter().getPhone())
                    .address(booking.getSitter().getAddress())
                    .email(booking.getSitter().getEmail())
                    .avatarImgUrl(booking.getSitter().getAvatarImgUrl())
                    .build();

            CustomerResponseDTO cusResponseDTO = CustomerResponseDTO.builder()
                    .fullName(booking.getUser().getFullName())
                    .dob(booking.getUser().getDob())
                    .address(booking.getUser().getAddress())
                    .gender(booking.getUser().getGender())
                    .phone(booking.getUser().getPhone())
                    .address(booking.getUser().getAddress())
                    .email(booking.getUser().getEmail())
                    .frontIdImgUrl(booking.getUser().getFrontIdImgUrl())
                    .backIdImgUrl(booking.getUser().getBackIdImgUrl())
                    .avatarImgUrl(booking.getUser().getAvatarImgUrl())
                    .status(booking.getUser().getStatus())
                    .build();
            List<BookingImg> bookingImgs = booking.getBookingImgs();
            List<BookingImgResponseDTO> bookingImgResponseDTOList = new ArrayList<>();
            if (bookingImgs.isEmpty()) {
                bookingImgResponseDTOList = null;
            } else {
                for (BookingImg bookingImg : bookingImgs
                ) {
                    BookingImgResponseDTO bookingImgResponseDTO = BookingImgResponseDTO.builder()
                            .localDateTime(bookingImg.getLocalDateTime())
                            .url(bookingImg.getUrl())
                            .build();
                    bookingImgResponseDTOList.add(bookingImgResponseDTO);
                }
            }
            PaymentResponseDTO paymentResponseDTO;
            if (booking.getPayment() == null) {
                paymentResponseDTO = null;
            } else {
                paymentResponseDTO = PaymentResponseDTO.builder()
                        .dateTime(booking.getPayment().getDateTime())
                        .amount(booking.getPayment().getAmount())
                        .build();
            }
            List<BookingDetailResponseDTO> bookingDetailResponseDTOList = new ArrayList<>();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            if (bookingDetails.isEmpty()) {
                bookingImgResponseDTOList = null;
            } else {
                for (BookingDetail bookingDetail : bookingDetails
                ) {
                    BookingDetailResponseDTO bookingDetailResponseDTO = BookingDetailResponseDTO.builder()
                            .id(bookingDetail.getId())
                            .serviceName(bookingDetail.getService().getName())
                            .commission(bookingDetail.getCommission())
                            .duration(bookingDetail.getDuration())
                            .price(bookingDetail.getPrice())
                            .build();
                    bookingDetailResponseDTOList.add(bookingDetailResponseDTO);
                }
            }
            List<WorkingTimeResponseDTO> workingTimeResponseDTOList = new ArrayList<>();
            List<WorkingTime> workingTimes = booking.getWorkingTimes();
            for (WorkingTime workingTime : workingTimes) {
                WorkingTimeResponseDTO workingTimeResponseDTO = WorkingTimeResponseDTO.builder()
                        .localDate(workingTime.getLocalDate())
                        .startTime(workingTime.getStartTime())
                        .endTime(workingTime.getEndTime())
                        .build();
                workingTimeResponseDTOList.add(workingTimeResponseDTO);
            }
            bookingResponseDTO = BookingResponseDTO.builder()
                    .address(booking.getAddress())
                    .description(booking.getDescription())
                    .elder(elderResponseDTO)
                    .place(booking.getPlace())
                    .sitterDTO(sitterDTO)
                    .customerResponseDTO(cusResponseDTO)
                    .totalPrice(booking.getTotalPrice())
                    .deposit(booking.getDeposit())
                    .status(booking.getStatus())
                    .bookingImgResponseDTOList(bookingImgResponseDTOList)
                    .workingTimeResponseDTOList(workingTimeResponseDTOList)
                    .bookingDetailResponseDTOList(bookingDetailResponseDTOList)
                    .paymentResponseDTO(paymentResponseDTO)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingResponseDTO;

    }

    public BookingDTO convertBookingToBookingDTO(Booking booking) {

        BookingDTO bookingDTO = null;
        try {
            ElderResponseDTO elderResponseDTO = ElderResponseDTO.builder()
                    .id(booking.getElder().getId())
                    .name(booking.getElder().getName())
                    .gender(booking.getElder().getGender())
                    .dob(booking.getElder().getDob())
                    .note(booking.getElder().getNote())
                    .isAllergy(booking.getElder().getIsAllergy())
                    .build();

            SitterDTO sitterDTO = SitterDTO.builder()
                    .id(booking.getSitter().getId())
                    .fullName(booking.getSitter().getFullName())
                    .dob(booking.getSitter().getDob())
                    .gender(booking.getSitter().getGender())
                    .phone(booking.getSitter().getPhone())
                    .address(booking.getSitter().getAddress())
                    .email(booking.getSitter().getEmail())
                    .avatarImgUrl(booking.getSitter().getAvatarImgUrl())
                    .build();

            CustomerResponseDTO cusResponseDTO = CustomerResponseDTO.builder()
                    .fullName(booking.getUser().getFullName())
                    .dob(booking.getUser().getDob())
                    .address(booking.getUser().getAddress())
                    .gender(booking.getUser().getGender())
                    .phone(booking.getUser().getPhone())
                    .address(booking.getUser().getAddress())
                    .email(booking.getUser().getEmail())
                    .frontIdImgUrl(booking.getUser().getFrontIdImgUrl())
                    .backIdImgUrl(booking.getUser().getBackIdImgUrl())
                    .avatarImgUrl(booking.getUser().getAvatarImgUrl())
                    .status(booking.getUser().getStatus())
                    .build();

            List<WorkingTimeResponseDTO> workingTimeResponseDTOList = new ArrayList<>();
            List<WorkingTime> workingTimes = booking.getWorkingTimes();
            for (WorkingTime workingTime : workingTimes) {
                WorkingTimeResponseDTO workingTimeResponseDTO = WorkingTimeResponseDTO.builder()
                        .localDate(workingTime.getLocalDate())
                        .startTime(workingTime.getStartTime())
                        .endTime(workingTime.getEndTime())
                        .build();
                workingTimeResponseDTOList.add(workingTimeResponseDTO);
            }

            List<BookingDetailResponseDTO> bookingDetailResponseDTOList = new ArrayList<>();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            for (BookingDetail bookingDetail : bookingDetails) {
                BookingDetailResponseDTO bookingDetailResponseDTO = BookingDetailResponseDTO.builder()
                        .id(bookingDetail.getId())
                        .serviceName(bookingDetail.getService().getName())
                        .commission(bookingDetail.getCommission())
                        .duration(bookingDetail.getDuration())
                        .price(bookingDetail.getPrice())
                        .build();
                bookingDetailResponseDTOList.add(bookingDetailResponseDTO);
            }
            bookingDTO = BookingDTO.builder()
                    .address(booking.getAddress())
                    .description(booking.getDescription())
                    .elder(elderResponseDTO)
                    .place(booking.getPlace())
                    .sitterDTO(sitterDTO)
                    .customerResponseDTO(cusResponseDTO)
                    .totalPrice(booking.getTotalPrice())
                    .workingTimeResponseDTOList(workingTimeResponseDTOList)
                    .bookingDetailResponseDTOList(bookingDetailResponseDTOList)
                    .deposit(booking.getDeposit())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingDTO;
    }


}