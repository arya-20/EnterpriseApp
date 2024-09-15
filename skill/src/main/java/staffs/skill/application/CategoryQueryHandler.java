package staffs.skill.application;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import staffs.skill.api.GetCategoryResponse;
import staffs.skill.domain.Category;
import staffs.skill.infrastructure.CategoryRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryQueryHandler {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Optional<GetCategoryResponse> getCategory(String categoryId) {
        return categoryRepository.findById(categoryId).map(category ->
                modelMapper.map(category, GetCategoryResponse.class));
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAllCategories();
    }
}