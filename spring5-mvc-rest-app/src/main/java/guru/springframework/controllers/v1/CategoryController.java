package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }
//    public ResponseEntity<CategoryListDTO> getAllCategories() {
//        return new ResponseEntity<CategoryListDTO>(
//                new CategoryListDTO(categoryService.getAllCategories()), HttpStatus.OK);
//    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable final String name) {
        return categoryService.getCategoryByName(name);
    }
//    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable final String name) {
//        return new ResponseEntity<CategoryDTO>(categoryService.getCategoryByName(name), HttpStatus.OK);
//    }
}
