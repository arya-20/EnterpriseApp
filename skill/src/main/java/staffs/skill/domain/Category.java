package staffs.skill.domain;

import example.common.domain.Entity;
import example.common.domain.Identity;
import staffs.skill.api.events.CategoryCreatedEvent;

import java.util.Optional;

public class Category extends Entity {
    private String name;

    // Factory method
    public static Category categoryOf(Identity id, String name) {
        return new Category(id, name);
    }

    public Category(Identity id, String name) {
        super(id);
        setName(name);
        event = Optional.of((new CategoryCreatedEvent( id.toString(), name)));
    }

    public String name() {return name;}

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Category name cannot be empty");
        this.name = name;
    }


    public String toString() {
        return String.format("Category: %s, Name: %s", id(), name);
    }

    public void updatedCategory(String categoryName) {
        this.name = categoryName;
    }
}
