package staffs.skill.application;


import staffs.skill.api.BaseCategory;
import staffs.skill.domain.Category;

public class CategoryDomainToInfrastructureConvertor {

    public static BaseCategory convert(Category category) {
        BaseCategory c = (BaseCategory) staffs.skill.infrastructure.Category.categoryOf(category.id().toString(),
                category.name());

        return c;
    }
}
