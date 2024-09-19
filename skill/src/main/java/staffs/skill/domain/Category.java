package staffs.skill.domain;

import example.common.domain.ValueObject;

public class Category extends ValueObject {
    private String name;
    private String id;


    public Category(String id, String name) {
        setId(id);
        setName(name);
    }


    public String id() {return this.id;}

    private void setId(String id) {
        assertArgumentNotEmpty(id, "Category id cannot be empty");
        this.id = id;
    }

    public String name() {return this.name;}

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
