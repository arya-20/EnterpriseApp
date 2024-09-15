package staffs.staffskill.api;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import staffs.staffskill.application.IdentityService;
import staffs.staffskill.application.StaffApplicationService;
import staffs.staffskill.application.StaffDomainException;
import staffs.staffskill.application.StaffQueryHandler;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/staff")
public class StaffController {
    private final IdentityService identityService;
    private StaffQueryHandler staffQueryHandler;
    private StaffApplicationService staffApplicationService;

    // Get all staff members, e.g. http://localhost:8900/staff/all
    @GetMapping(path = "/all")
    public ResponseEntity<?>  findAll(@RequestHeader("Authorization") String token) {
        try {
            if (identityService.isAdmin(token)) {
                return ResponseEntity.ok().body(staffQueryHandler.getAllStaff());
            }
        }
        catch (JwtException jwtException){
            return generateErrorResponse(jwtException.getMessage());
        }
        catch (IllegalArgumentException iae) {}
        return generateErrorResponse("user not authorised");
    }

    // Get a specific staff member by ID, e.g. http://localhost:8900/staff/{staffId}
    @GetMapping(path = "/{staffId}")
    public ResponseEntity<?> findById(@PathVariable String staffId,
                                      @RequestHeader("Authorization") String token) {
        try {
            //Valid user or ADMIN?
            if (identityService.isAdmin(token) ||
                    identityService.isSpecifiedUser(token, staffId)) {
                return staffQueryHandler.getStaff(staffId).map(
                                o -> new ResponseEntity<>(o, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        }
        catch (JwtException jwtException){
            return generateErrorResponse(jwtException.getMessage());
        }
        catch (IllegalArgumentException iae) {}
        return generateErrorResponse("user not authorised");
    }

    // Get all skills for a staff member by their ID, e.g. http://localhost:8900/staff/skills/{staffId}
    @GetMapping(path = "/skills/{staffId}")
    public ResponseEntity<?> findSkillByStaffId(@PathVariable String staffId,
                                                    @RequestHeader("Authorization") String token) {
        try {
            //Valid user or ADMIN?
            if (identityService.isAdmin(token)||
                    identityService.isSpecifiedUser(token, staffId)) {
                return staffQueryHandler.getStaffskill(staffId).map(
                                o -> new ResponseEntity<>(o, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        }
        catch (JwtException jwtException){
            return generateErrorResponse(jwtException.getMessage());
        }
        catch (IllegalArgumentException iae) {}
        return generateErrorResponse("user not authorised");
    }

    /** POST: Create a new staff member with skills, e.g.
     POST http://localhost:8900/staff
     {
     "fullName": "Tony Stark",
     "managerId": "301",
     "role": "Software Engineer",
     "staffSkills": [
     { "id": "5", "skillName": "Java Programming", "proficiencyLevel": "Advanced", "expiryDate": "2024-09-15", "levelOfSkill": "Expert", "notes": "..."}
     ]
     }

     **/
    @PostMapping
    public ResponseEntity<?> createStaffWithSkills(@RequestBody CreateStaffCommand command,
                                                      @RequestHeader("Authorization") String token){
        try{
            //Valid ADMIN?
            if(identityService.isAdmin(token)) {
                return new ResponseEntity<>(staffApplicationService.createStaffWithSkills(command),
                        HttpStatus.CREATED);
            }
        }
        catch (JwtException jwtException){
            return generateErrorResponse(jwtException.getMessage());
        }
        catch(StaffDomainException | JsonParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create staff member");
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
        return generateErrorResponse("user not authorised");
    }

    private ResponseEntity<?> generateErrorResponse(String message){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
