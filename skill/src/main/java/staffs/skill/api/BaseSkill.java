package staffs.skill.api;


import staffs.skill.infrastructure.SkillDetail;

import java.util.List;

public interface BaseSkill {
    String getId();

    String getName();

    String getCategory();

    List<SkillDetail> getSkillDetails();

    void addSkillDetail(staffs.skill.infrastructure.SkillDetail skillDetail);  //Used in application layer when converting between entity and domain

}
