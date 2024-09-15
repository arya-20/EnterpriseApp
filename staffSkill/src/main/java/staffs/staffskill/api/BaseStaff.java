package staffs.staffskill.api;

import staffs.staffskill.infrastructure.StaffSkill;

import java.util.List;

public interface BaseStaff {
        String getId();

        String getFullName();

        String getManagerId();

        String getRole();

        List<StaffSkill> getStaffSkills();

        void addStaffSkill(StaffSkill staffSkill);


    }

