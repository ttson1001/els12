package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.BookingDTO;
import elderlysitter.capstone.dto.ReduceDateDTO;
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
import java.time.LocalDate;
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
    public BigDecimal payForSitter(Long bookingId) {
        BigDecimal sitterTotal = BigDecimal.valueOf(0);
        BigDecimal workingTime = BigDecimal.valueOf(workingTimeRepository.findAllByBooking_IdAndStatus(bookingId,"DONE").size());
        try {
            Booking booking = bookingRepository.findById(bookingId).get();
            BigDecimal total = booking.getTotalPrice().divide(BigDecimal.valueOf(booking.getWorkingTimes().size()));
            BigDecimal deposit = booking.getDeposit().divide(BigDecimal.valueOf(booking.getWorkingTimes().size()));
            total = total.multiply(workingTime);
            deposit =deposit.multiply(workingTime);
            sitterTotal = total.add(deposit);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sitterTotal;
    }

    @Override
    public BookingDTO addBooking(AddBookingRequestDTO addBookingRequestDTO) {
        UUID uuid = UUID.randomUUID();
        BookingDTO bookingDTO = null;
        BigDecimal total = BigDecimal.valueOf(0);
        BigDecimal deposit = BigDecimal.valueOf(0);
        try {
            List<AddWorkingTimesRequestDTO> addWorkingTimesRequestDTOS = addBookingRequestDTO.getAddWorkingTimesDTOList();
            List<AddBookingServiceRequestDTO> addBookingServiceRequestDTOS = addBookingRequestDTO.getAddBookingServiceRequestDTOS();

            User sitter = userService.randomSitter(addBookingServiceRequestDTOS, "");
            if (sitter == null) {
                Booking booking = Booking.builder()
                        .name(uuid.toString())
                        .address(addBookingRequestDTO.getAddress())
                        .place(addBookingRequestDTO.getPlace())
                        .description(addBookingRequestDTO.getDescription())
                        .status(StatusCode.CANCEL.toString())
                        .elder(elderRepository.findById(addBookingRequestDTO.getElderId()).get())
                        .sitter(null)
                        .deposit(null)
                        .totalPrice(null)
                        .user(userRepository.findUserByEmail(addBookingRequestDTO.getEmail()))
                        .build();
                bookingRepository.save(booking);
                Booking newBooking = bookingRepository.findBookingByName(booking.getName());
                for (int i = 0; i < addBookingRequestDTO.getAddBookingServiceRequestDTOS().size(); i++) {
                    Service service = serviceRepository.findById(addBookingServiceRequestDTOS.get(i).getId()).get();
                    BookingDetail bookingDetail = BookingDetail.builder()
                            .booking(newBooking)
                            .service(service)
                            .serviceName(service.getName())
                            .commission(service.getCommission())
                            .duration(addBookingRequestDTO.getAddBookingServiceRequestDTOS().get(i).getDuration())
                            .build();
                    bookingDetailRepository.save(bookingDetail);
                }
                for (AddWorkingTimesRequestDTO addWorkingTimesRequestDTO : addWorkingTimesRequestDTOS) {
                    WorkingTime workingTime = WorkingTime.builder()
                            .booking(newBooking)
                            .startDateTime(addWorkingTimesRequestDTO.getStartDateTime())
                            .endDateTime(addWorkingTimesRequestDTO.getEndDateTime())
                            .status("ACTIVATE")
                            .build();
                    workingTimeRepository.save(workingTime);
                }
                bookingDTO = convertBookingToBookingDTO(newBooking);
                return bookingDTO;
            }

            for (AddBookingServiceRequestDTO addBookingServiceRequestDTO : addBookingServiceRequestDTOS) {
                SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(sitter.getEmail(), addBookingServiceRequestDTO.getId());
                Long commission = sitterService.getService().getCommission();
                deposit = deposit.add(sitterService.getPrice().multiply(BigDecimal.valueOf(addBookingServiceRequestDTO.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))).multiply((BigDecimal.valueOf(commission).divide(BigDecimal.valueOf(100)))));
                deposit = deposit.multiply(BigDecimal.valueOf(addWorkingTimesRequestDTOS.size()));
                total = total.add(sitterService.getPrice().multiply(BigDecimal.valueOf(addBookingServiceRequestDTO.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))));
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
            for (AddBookingServiceRequestDTO addBookingServiceRequestDTO : addBookingServiceRequestDTOS) {
                Service service = serviceRepository.findById(addBookingServiceRequestDTO.getId()).get();
                SitterService sitterService = sitterServiceRepository.findBySitterProfile_User_EmailAndService_Id(sitter.getEmail(), service.getId());
                BigDecimal price = (sitterService.getPrice().multiply(BigDecimal.valueOf(addBookingServiceRequestDTO.getDuration()).divide(BigDecimal.valueOf(sitterService.getService().getDuration()))));
                BookingDetail bookingDetail = BookingDetail.builder()
                        .booking(newBooking)
                        .service(service)
                        .serviceName(service.getName())
                        .commission(service.getCommission())
                        .duration(addBookingServiceRequestDTO.getDuration() * addWorkingTimesRequestDTOS.size())
                        .price(price.multiply(BigDecimal.valueOf(addWorkingTimesRequestDTOS.size())))
                        .build();
                bookingDetailRepository.save(bookingDetail);
            }
            for (AddWorkingTimesRequestDTO addWorkingTimesRequestDTO : addWorkingTimesRequestDTOS) {
                WorkingTime workingTime = WorkingTime.builder()
                        .booking(newBooking)
                        .startDateTime(addWorkingTimesRequestDTO.getStartDateTime())
                        .endDateTime(addWorkingTimesRequestDTO.getEndDateTime())
                        .status("ACTIVATE")
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
        List<BookingsResponseDTO> bookingsResponseDTOS = new ArrayList<>();
        try {
            List<Booking> bookings = bookingRepository.findAll();
            for (Booking booking : bookings) {
                BookingsResponseDTO bookingsResponseDTO = BookingsResponseDTO.builder()
                        .id(booking.getId())
                        .name(booking.getName())
                        .sitterName(booking.getSitter() == null ? null : booking.getSitter().getFullName())
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
    public AdminBookingResponseDTO getBookingByIdForAdmin(Long id) {
        AdminBookingResponseDTO adminBookingResponseDTO = null;
        try {
            Long totalTime = 0L;
            Booking booking = bookingRepository.findById(id).get();
            List<BookingDetailResponseDTO> bookingDetailResponseDTOList = new ArrayList<>();
            List<BookingImgResponseDTO> bookingImgResponseDTOList = new ArrayList<>();
            List<BookingImg> bookingImgs = booking.getBookingImgs();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            if (bookingDetails.isEmpty()) {
                bookingDetailResponseDTOList = null;
            } else {
                for (BookingDetail bookingDetail : bookingDetails
                ) {
                    totalTime = totalTime + bookingDetail.getDuration();
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
            if (bookingImgs.isEmpty()) {
                bookingImgResponseDTOList = null;
            } else {
                for (BookingImg bookingImg : bookingImgs
                ) {
                    BookingImgResponseDTO bookingImgResponseDTO = BookingImgResponseDTO.builder()
                            .localDateTime(bookingImg.getLocalDateTime())
                            .checkInUrl(bookingImg.getCheckInUrl())
                            .checkOutUrl(bookingImg.getCheckOutUrl())
                            .build();
                    bookingImgResponseDTOList.add(bookingImgResponseDTO);
                }
            }

            adminBookingResponseDTO = AdminBookingResponseDTO.builder()
                    .address(booking.getAddress())
                    .description(booking.getDescription())
                    .place(booking.getPlace())
                    .sitterName(booking.getSitter().getFullName())
                    .customerName(booking.getUser().getFullName())
                    .totalPrice(booking.getTotalPrice())
                    .totalTime(totalTime)
                    .startDate(booking.getWorkingTimes().get(0).getStartDateTime().toLocalDate())
                    .endDate(booking.getWorkingTimes().get(booking.getWorkingTimes().size() - 1).getEndDateTime().toLocalDate())
                    .bookingDetailResponseDTOList(bookingDetailResponseDTOList)
                    .bookingImgResponseDTOList(bookingImgResponseDTOList)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminBookingResponseDTO;
    }

    @Override
    public List<BookingsResponseDTO> getAllBookingByStatus(String status) {
        List<BookingsResponseDTO> bookingsResponseDTOS = new ArrayList<>();
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
            booking.setStatus(StatusCode.WAITING_FOR_CUSTOMER_PAYMENT.toString());
            booking = bookingRepository.save(booking);
            bookingDTO = convertBookingToBookingDTO(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingDTO;
    }

    @Override
    public List<BookingsResponseDTO> findAllByCustomerEmail(String customerEmail) {
        List<BookingsResponseDTO> bookingsResponseDTOS = new ArrayList<>();
        try {
            List<Booking> bookings = bookingRepository.findAllByUser_Email(customerEmail);
            for (Booking booking : bookings) {
                BookingsResponseDTO bookingsResponseDTO = BookingsResponseDTO.builder()
                        .id(booking.getId())
                        .name(booking.getName())
                        .sitterName(booking.getSitter() == null ? null : booking.getSitter().getFullName())
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
    public List<BookingsResponseDTO> findAllBySitterEmail(String sitterEmail) {
        List<BookingsResponseDTO> bookingsResponseDTOS = new ArrayList<>();
        try {
            List<Booking> bookings = bookingRepository.findAllBySitter_Email(sitterEmail);
            for (Booking booking : bookings) {
                BookingsResponseDTO bookingsResponseDTO = BookingsResponseDTO.builder()
                        .id(booking.getId())
                        .name(booking.getName())
                        .sitterName(booking.getSitter() == null ? null : booking.getSitter().getFullName())
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
    public List<BookingsResponseDTO> findAllByCustomerEmailAndStatus(String customerEmail, String status) {
        List<BookingsResponseDTO> bookingsResponseDTOS = new ArrayList<>();
        try {
            List<Booking> bookings = bookingRepository.findAllByUser_EmailAndStatus(customerEmail, status);
            for (Booking booking : bookings) {
                BookingsResponseDTO bookingsResponseDTO = BookingsResponseDTO.builder()
                        .id(booking.getId())
                        .name(booking.getName())
                        .sitterName(booking.getSitter() == null ? null : booking.getSitter().getFullName())
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
    public List<BookingsResponseDTO> findAllBySitter_EmailAndStatus(String sitterEmail, String status) {
        List<BookingsResponseDTO> bookingsResponseDTOS = new ArrayList<>();
        try {
            List<Booking> bookings = bookingRepository.findAllBySitter_EmailAndStatus(sitterEmail, status);
            for (Booking booking : bookings) {
                BookingsResponseDTO bookingsResponseDTO = BookingsResponseDTO.builder()
                        .id(booking.getId())
                        .name(booking.getName())
                        .sitterName(booking.getSitter() == null ? null : booking.getSitter().getFullName())
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
    public Long countBooking(String startDate, String endDate) {
        Long count = 0l;
        try {
            count = bookingRepository.countBookingOnMonth(LocalDate.parse(startDate), LocalDate.parse(endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public BigDecimal sumDeposit(String startDate, String endDate) {
        BigDecimal sum = BigDecimal.valueOf(0);
        try {
            sum = bookingRepository.totalRevenue(LocalDate.parse(startDate), LocalDate.parse(endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    public BookingsResponseDTO checkBookingForCustomer(Long bookingId) {
        BookingsResponseDTO bookingsResponseDTO = null;
        try {
            Booking booking = bookingRepository.findById(bookingId).get();
            booking.setStatus(StatusCode.DONE.toString());
            booking = bookingRepository.save(booking);
            bookingsResponseDTO = BookingsResponseDTO.builder()
                    .id(booking.getId())
                    .name(booking.getName())
                    .sitterName(booking.getSitter().getFullName())
                    .place(booking.getPlace())
                    .deposit(booking.getDeposit())
                    .totalPrice(booking.getTotalPrice())
                    .status(booking.getStatus())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingsResponseDTO;
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
                    .healthStatus(booking.getElder().getHealthStatus())
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
                            .checkInUrl(bookingImg.getCheckInUrl())
                            .checkOutUrl(bookingImg.getCheckOutUrl())
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
                bookingDetailResponseDTOList = null;
            } else {
                for (BookingDetail bookingDetail : bookingDetails
                ) {
                    BookingDetailResponseDTO bookingDetailResponseDTO = BookingDetailResponseDTO.builder()
                            .id(bookingDetail.getId())
                            .serviceName(bookingDetail.getServiceName())
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
                        .endDateTime(workingTime.getEndDateTime())
                        .startDateTime(workingTime.getStartDateTime())
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
                        .endDateTime(workingTime.getEndDateTime())
                        .startDateTime(workingTime.getStartDateTime())
                        .build();
                workingTimeResponseDTOList.add(workingTimeResponseDTO);
            }

            List<BookingDetailResponseDTO> bookingDetailResponseDTOList = new ArrayList<>();
            List<BookingDetail> bookingDetails = booking.getBookingDetails();
            for (BookingDetail bookingDetail : bookingDetails) {
                BookingDetailResponseDTO bookingDetailResponseDTO = BookingDetailResponseDTO.builder()
                        .id(bookingDetail.getId())
                        .serviceName(bookingDetail.getServiceName())
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
