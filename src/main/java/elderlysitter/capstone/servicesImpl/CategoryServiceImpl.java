package elderlysitter.capstone.servicesImpl;

import elderlysitter.capstone.dto.CategoryDTO;
import elderlysitter.capstone.dto.request.AddCategoryRequestDTO;
import elderlysitter.capstone.entities.Category;
import elderlysitter.capstone.repository.CategoryRepository;
import elderlysitter.capstone.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(AddCategoryRequestDTO addCategoryRequestDTO) {
        Category category = null;
        try {
            category = Category.builder()
                    .name(addCategoryRequestDTO.getName())
                    .build();
            category = categoryRepository.save(category);
        }catch (Exception e){
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = null;
        try {
            categories = categoryRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category update(CategoryDTO categoryDTO) {
        Category category = null;
        try {
            category = categoryRepository.findById(categoryDTO.getId()).get();
            category.setName(category.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return category;

    }
}
