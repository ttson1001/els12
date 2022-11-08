package elderlysitter.capstone.services;

import elderlysitter.capstone.dto.request.AddCategoryRequestDTO;
import elderlysitter.capstone.entities.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(AddCategoryRequestDTO addCategoryRequestDTO);

    List<Category> getAllCategory();
}
