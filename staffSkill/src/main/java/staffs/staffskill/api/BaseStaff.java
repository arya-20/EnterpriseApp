package staffs.staffskill.api;

import example.common.domain.FullName;
import staffs.staffskill.infrastructure.StaffSkill;

import java.util.List;

public interface BaseStaff {
        String getId();

        FullName getFullName();

        String getManagerId();

        String getRole();

        List<StaffSkill> getStaffSkills();

        void addStaffSkill(StaffSkill staffSkill);


    }

