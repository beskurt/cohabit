package eu.qerkinaj.kobutsu.marketplace.listing.service;

import eu.qerkinaj.kobutsu.marketplace.listing.domain.Category;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.CategoryBaseDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.CategoryDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.mapper.CategoryMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Inject
    CategoryMapper categoryMapper;

    @Transactional
    public CategoryDTO create(CategoryBaseDTO dto, UUID parent) {
        log.debug("Creating category {}", dto.getName());
        log.debug("creating {}", dto);

        Category category = new Category();
        category.name = dto.getName();

        log.debug("creating {}", category);

        if (parent != null) {
            Category parentCategory = Category.findCategoryById(parent);

            if (parentCategory == null) {
                log.error("parent category not found");
                throw new NotFoundException("Parent category not found");
            }
            category.parent = parentCategory;
            parentCategory.children.add(category);
        }
        category.persist();
        return categoryMapper.toCompleteDto(category);
    }

    @Transactional
    public CategoryDTO updateCategory(UUID parentId, CategoryDTO dto) {
        log.debug("updating {}", dto);

        Category entity = Category.findById(parentId);
        if (entity == null) {
            throw new NotFoundException("Category not found");
        }

        entity.name = dto.getName();
        UUID newParentId = dto.getParentId();

        if (newParentId != null) {
            log.debug("updating parent category {}", newParentId);
            if (entity.parent == null || !newParentId.equals(entity.parent.id)) {
                Category newParent = Category.findById(newParentId);
                if (newParent == null) {
                    throw new NotFoundException("Parent category not found");
                }
                entity.parent = newParent;
            }
        } else {
            entity.parent = null;
        }

        return categoryMapper.toCompleteDto(entity);
    }

    public CategoryDTO getCategory(UUID id) {
        Category entity = Category.findById(id);
        if (entity == null) {
            throw new NotFoundException("Category not found");
        }
        return categoryMapper.toCompleteDto(entity);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toDtoList(Category.listAll());
    }

    @Transactional
    public void deleteCategory(UUID id) {
        boolean deleted = Category.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Category not found");
        }
    }
}
