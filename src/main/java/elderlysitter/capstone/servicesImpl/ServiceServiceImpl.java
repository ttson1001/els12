package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.ServiceDTO;
import elderlysitter.capstone.dto.request.AddServiceRequestDTO;
import elderlysitter.capstone.dto.request.UpdateServiceRequestDTO;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.enumCode.StatusCode;
import elderlysitter.capstone.repository.BookingDetailRepository;
import elderlysitter.capstone.repository.CategoryRepository;
import elderlysitter.capstone.repository.ServiceRepository;
import elderlysitter.capstone.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Override
    public Service addService(AddServiceRequestDTO addServiceRequestDTO) {
        Service service = null;
        try {
            service = Service.builder()
                    .name(addServiceRequestDTO.getName())
                    .price(addServiceRequestDTO.getPrice())
                    .description(addServiceRequestDTO.getDescription())
                    .url(addServiceRequestDTO.getUrl())
                    .sitterRequirement(addServiceRequestDTO.getSitterRequirement())
                    .duration(addServiceRequestDTO.getDuration())
                    .commission(addServiceRequestDTO.getCommission())
                    .status(StatusCode.ACTIVATE.toString())
                    .category(categoryRepository.findById(addServiceRequestDTO.getCategoryId()).get())
                    .build();
            service = serviceRepository.save(service);
        }catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }
    @Override
    public Service updateService(UpdateServiceRequestDTO updateServiceRequestDTO) {
        Service service = null;
        try {
            service = serviceRepository.findById(updateServiceRequestDTO.getId()).get();
            service.setName(updateServiceRequestDTO.getName());
            service.setPrice(updateServiceRequestDTO.getPrice());
            service.setDescription(updateServiceRequestDTO.getDescription());
            service.setUrl(updateServiceRequestDTO.getUrl());
            service.setSitterRequirement(updateServiceRequestDTO.getSitterRequirement());
            service.setDuration(updateServiceRequestDTO.getDuration());
            service.setCommission(updateServiceRequestDTO.getCommission());
            service.setCategory(categoryRepository.findById(updateServiceRequestDTO.getCategoryId()).get());
            service = serviceRepository.save(service);
        }catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }

    @Override
    public Service activeService(Long id) {
        Service service = null;
        try {
            service = serviceRepository.findById(id).get();
            service.setStatus(StatusCode.ACTIVATE.toString());
            serviceRepository.save(service);
        }catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }

    @Override
    public Service deactivateService(Long id) {
        Service service = null;
        try {
            service = serviceRepository.findById(id).get();
            service.setStatus(StatusCode.DEACTIVATE.toString());
            serviceRepository.save(service);
        }catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }

    @Override
    public List<Service> getAllService() {
        List<Service> services = null;
        try {
            services = serviceRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public List<Service> getAllServiceByActive() {
        List<Service> services = null;
        try {
            services = serviceRepository.findAllByStatus(StatusCode.ACTIVATE.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public Service getServiceById(Long id) {
        Service service = null;
        try {
            service = serviceRepository.findById(id).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }

    @Override
    public List<ServiceDTO> reportService() {
        List<ServiceDTO> serviceDTOS = new ArrayList<>();
        try {
            serviceDTOS = bookingDetailRepository.reportService();
        }catch (Exception e){
            e.printStackTrace();
        }
        return serviceDTOS;
    }

    @Override
    public List<Service> getAllServiceByCategoryID(Long id) {
        List<Service> services = new ArrayList<>();
        try {
            services = serviceRepository.findAllByCategory_Id(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return services;
    }


}
