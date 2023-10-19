package slovachevska.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import slovachevska.onlinebookstore.dto.category.CategoryRequestDto;
import slovachevska.onlinebookstore.dto.category.CategoryResponseDto;
import slovachevska.onlinebookstore.mapper.CategoryMapper;
import slovachevska.onlinebookstore.model.Category;
import slovachevska.onlinebookstore.repository.category.CategoryRepository;
import slovachevska.onlinebookstore.service.category.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Check if the category is in the database after saving")
    public void save_ValidCategory_ReturnValidCategoryRequestDto() {
        Category category = getCategory();
        CategoryRequestDto categoryRequestDto = getCategoryRequestDto();
        CategoryResponseDto categoryResponseDto = getCategoryResponseDto();

        when(categoryRepository.save(category)).thenReturn(category.setId(1L));
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);
        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);

        CategoryResponseDto actual = categoryService.save(categoryRequestDto);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(categoryResponseDto, actual);
    }

    @Test
    @DisplayName("Find all categories")
    public void findAll_ValidCategory_ReturnOneCategory() {
        Category category = getCategory();
        CategoryResponseDto categoryResponseDto = getCategoryResponseDto();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> page = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);

        List<CategoryResponseDto> actual = categoryService.findAll(pageable);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(categoryResponseDto, actual.get(0));
    }

    private Category getCategory() {
        return new Category()
                .setName("Category")
                .setDescription("Description");
    }

    private CategoryResponseDto getCategoryResponseDto() {
        return new CategoryResponseDto()
                .setId(1L)
                .setName("Category")
                .setDescription("Description");

    }

    private CategoryRequestDto getCategoryRequestDto() {
        return new CategoryRequestDto()
                .setName("Category")
                .setDescription("Description");
    }

}
