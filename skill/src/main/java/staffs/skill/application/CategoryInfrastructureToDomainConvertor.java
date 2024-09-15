package staffs.skill.application;

import example.common.domain.Identity;
import staffs.skill.api.BaseCategory;
import staffs.skill.domain.Category;


public class CategoryInfrastructureToDomainConvertor {

    public static Category convert(BaseCategory category) {
        return Category.categoryOf(new Identity(category.getId()),
                category.getName());
    }
}