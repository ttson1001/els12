package elderlysitter.capstone.ServicesImpl;

import elderlysitter.capstone.Services.ServiceService;
import elderlysitter.capstone.dto.ServiceDTO;
import elderlysitter.capstone.entities.Service;
import elderlysitter.capstone.repository.CategoryRepository;
import elderlysitter.capstone.repository.ServiceRepository;
import elderlysitter.capstone.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public Service createService(ServiceDTO serviceDTO) {

        Service newService = serviceRepository.save(Service.builder()
                        .name(serviceDTO.getName())
                        .price(serviceDTO.getPrice())
                        .description(serviceDTO.getDescription())
                        .url(serviceDTO.getUrl())
                        .sitterRequirement(serviceDTO.getSitterRequirement())
                        .duration(serviceDTO.getDuration())
                        .status(statusRepository.findById(serviceDTO.getStatusID()).get())
                        .category(categoryRepository.findById(serviceDTO.getCategoryID()).get())
                        .build());
        return newService;
    }

    public Service updateService(ServiceDTO serviceDTO){
        Service oldService = serviceRepository.findById(serviceDTO.getId()).get();
        oldService.setName(serviceDTO.getName());
        oldService.setDescription(serviceDTO.getDescription());
        oldService.setPrice(serviceDTO.getPrice());
        oldService.setUrl(serviceDTO.getUrl());
        oldService.setSitterRequirement(serviceDTO.getSitterRequirement());
        oldService.setDuration(serviceDTO.getDuration());
        oldService.setStatus(statusRepository.findById(serviceDTO.getStatusID()).get());
        oldService.setCategory(categoryRepository.findById(serviceDTO.getCategoryID()).get());

        return serviceRepository.save(oldService);
    }

    public List<Service> getAllService(){
        try {
            ArrayList<Service> services = (ArrayList<Service>) serviceRepository.findAll();
            return  services;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
