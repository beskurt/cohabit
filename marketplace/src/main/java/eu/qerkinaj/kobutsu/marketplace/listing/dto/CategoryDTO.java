package eu.qerkinaj.kobutsu.marketplace.listing.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"id", "children"})
public class CategoryDTO extends CategoryBaseDTO {

    private UUID id;

    private UUID parentId;

    private List<CategoryDTO> children;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public List<CategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryDTO> children) {
        this.children = children;
    }
}