package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import eu.qerkinaj.kobutsu.marketplace.currency.MoneyDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.enums.Condition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@JsonPropertyOrder({"id", "seller"})
public class ListingBaseDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @Size(max = 1000, message = "Description must be at most 1,000 characters")
    private String description;

    @NotNull(message = "Category is required")
    @Valid
    private CategoryBaseDTO category;

    @NotNull(message = "Condition is required")
    private Condition condition;

    @NotNull(message = "Price is required")
    @Valid
    private MoneyDTO price;

    private List<ListingImageDTO> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryBaseDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryBaseDTO category) {
        this.category = category;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public MoneyDTO getPrice() {
        return price;
    }

    public void setPrice(MoneyDTO price) {
        this.price = price;
    }

    public List<ListingImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ListingImageDTO> images) {
        this.images = images;
    }

}
