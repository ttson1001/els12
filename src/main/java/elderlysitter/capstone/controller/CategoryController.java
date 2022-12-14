package elderlysitter.capstone.controller;

import elderlysitter.capstone.dto.CategoryDTO;
import elderlysitter.capstone.dto.ResponseDTO;
import elderlysitter.capstone.dto.request.AddCategoryRequestDTO;
import elderlysitter.capstone.entities.Category;
import elderlysitter.capstone.enumCode.ErrorCode;
import elderlysitter.capstone.enumCode.SuccessCode;
import elderlysitter.capstone.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> addCategory(@RequestBody AddCategoryRequestDTO addCategoryRequestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Category category = categoryService.addCategory(addCategoryRequestDTO);
            responseDTO.setData(category);
            if(category != null){
                responseDTO.setSuccessCode(SuccessCode.ADD_CATEGORY_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.ADD_CATEGORY_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.ADD_CATEGORY_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("categories")
    @PermitAll
    public ResponseEntity<ResponseDTO> getAllCategory(){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Category> categories = categoryService.getAllCategory();
            responseDTO.setData(categories);
            if(categories != null){
                responseDTO.setSuccessCode(SuccessCode.FIND_ALL_CATEGORY_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.FIND_ALL_CATEGORY_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("update")
    @PermitAll
    public ResponseEntity<ResponseDTO> update (@RequestBody CategoryDTO categoryDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Category category = categoryService.update(categoryDTO);
            responseDTO.setData(category);
            if(category != null){
                responseDTO.setSuccessCode(SuccessCode.UPDATE_SUCCESS);
            }else{
                responseDTO.setErrorCode(ErrorCode.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorCode(ErrorCode.UPDATE_ERROR);
        }
        return ResponseEntity.ok().body(responseDTO);
    }


}
