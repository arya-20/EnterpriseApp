package staffs.staffskill.api;

import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import staffs.staffskill.application.StaffApplicationService;
import staffs.staffskill.application.StaffDomainException;
import staffs.staffskill.application.StaffQueryHandler;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/staff")
public class StaffController {
    private StaffQueryHandler staffQueryHandler;
    private StaffApplicationService staffApplicationService;

    // Get all staff members, e.g. http://localhost:8900/staff/all
    @GetMapping(path = "/all")
    public Iterable<BaseStaff> findAll() {
        return staffQueryHandler.getAllStaff();
    }

    // Get a specific staff member by ID, e.g. http://localhost:8900/staff/{staffId}
    @GetMapping(path = "/{staffId}")
    public ResponseEntity<GetStaffResponse> findById(@PathVariable String staffId) {
        return staffQueryHandler.getStaff(staffId).map(
                        o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all skills for a staff member by their ID, e.g. http://localhost:8900/staff/skills/{staffId}
    @GetMapping(path = "/skills/{staffId}")
    public ResponseEntity<GetStaffSkillResponse> findSkillsByStaffId(@PathVariable String staffId) {
        return staffQueryHandler.getStaffskill(staffId).map(
                        o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /** POST: Create a new staff member with skills, e.g.
     POST http://localhost:8900/staff
     {
     "fullName": "John Doe",
     "role": "Developer",
     "staffSkills": [
     {"name": "Java", "levelOfSkill": 4, "expiryDate": "2025-12-31"}
     ]
     }
     **/
    @PostMapping
    public ResponseEntity<String> createStaffWithSkills(@RequestBody CreateStaffCommand command) {
        try {
            return new ResponseEntity<>(staffApplicationService.createStaffWithSkills(command), HttpStatus.CREATED);
        } catch (StaffDomainException | JsonParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create staff");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
}


//add endpoints