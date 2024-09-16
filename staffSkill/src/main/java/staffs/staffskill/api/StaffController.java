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

import java.util.List;

//get all staff
//get staff by id
//get staff allocated to manager
//get skills allocated to staff member
//create new staff (not working)
//update staff with details
//delete staff

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

    // Get a staff member allocated to manager ID, e.g. http://localhost:8900/staff/manager/{managerId}
    @GetMapping(path = "/manager/{managerId}")
    public ResponseEntity<?> findByManagerId(@PathVariable String managerId,
                                             @RequestHeader("Authorization") String token) {
        try {
            if (identityService.isAdmin(token) || identityService.isSpecifiedUser(token, managerId)) {
                List<BaseStaff> staffList = staffQueryHandler.getStaffByManagerId(managerId);
                return ResponseEntity.ok(staffList);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        }
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
        catch (JwtException jwtException) {
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

    @PutMapping("/{staffId}")
    public ResponseEntity<?> updateStaffWithDetails(@PathVariable String staffId,
                                                    @RequestBody CreateStaffCommand command,
                                                    @RequestHeader("Authorization") String token) {
        try {
            // Valid ADMIN?
            if (identityService.isAdmin(token)) {
                staffApplicationService.updateStaffWithDetails(staffId, command);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (StaffDomainException | JsonParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update staff details");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
        return generateErrorResponse("User not authorized");
    }

    @DeleteMapping("/{staffId}")
    public ResponseEntity<?> removeStaff(@PathVariable String staffId, @RequestHeader("Authorization") String token) {
        try {
            // Ensure only admins can delete staff
            if (identityService.isAdmin(token)) {
                staffApplicationService.removeStaff(staffId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (StaffDomainException e) {
            return generateErrorResponse("Unable to remove staff member");
        }
        return generateErrorResponse("User not authorized");
    }



    private ResponseEntity<?> generateErrorResponse(String message){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
