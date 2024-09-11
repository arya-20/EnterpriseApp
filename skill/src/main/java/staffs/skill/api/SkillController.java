package staffs.skill.api;

import io.jsonwebtoken.JwtException;
import staffs.skill.application.IdentityService;
import staffs.skill.application.SkillApplicationService;
import staffs.skill.application.SkillQueryHandler;
import staffs.skill.application.SkillDomainException;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/skills")
public class SkillController {
    private final IdentityService identityService;
    private SkillQueryHandler skillQueryHandler;
    private SkillApplicationService skillApplicationService;

    // e.g. http://localhost:8080/skills/all
    @GetMapping(path="/all")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) {
        try {
            if (identityService.isAdmin(token)) {
                return ResponseEntity.ok().body(skillQueryHandler.getAllSkills());
            }
        }
        catch (JwtException jwtException){
            return generateErrorResponse(jwtException.getMessage());

        }
        catch (IllegalArgumentException iae) {}
        return generateErrorResponse("user not authorised");
    }


    // e.g. http://localhost:8080/skills/s1
    @GetMapping(path="/{skillId}")
    public ResponseEntity<?> findById(@PathVariable String skillId,
                                      @RequestHeader("Authorization") String token) {
        try {
            //Valid user or ADMIN?
            if (identityService.isAdmin(token) ||
                    identityService.isSpecifiedUser(token, skillId)) {
                return skillQueryHandler.getSkill(skillId).map(
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

    //e.g. http://localhost:8080/skills/skillDetails/s1
    @GetMapping(path="/skillDetails/{skillId}")
    public ResponseEntity<?> findSkillDetailBySkillId(@PathVariable String skillId,
                                                    @RequestHeader("Authorization") String token) {
        try {
            //Valid user or ADMIN?
            if (identityService.isAdmin(token)||
                    identityService.isSpecifiedUser(token, skillId)) {
                return skillQueryHandler.getSkillDetailResponse(skillId).map(
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

    // e.g. POST http://localhost:8080/skills
    /**
     {
     "skillName": "Java Programming",
     "category": "Technical",
     "skillDetail": [
     {
     "id": "1",
     "name": "OOP Principles",
     "proficiencyLevel": "Advanced"
     }
     ]
     }
     **/
    @PostMapping
    public ResponseEntity<?> createSkillWithDetails(@RequestBody CreateSkillCommand command,
                                                         @RequestHeader("Authorization") String token){
            try{
                //Valid ADMIN?
                if(identityService.isAdmin(token)) {
                    return new ResponseEntity<>(skillApplicationService.createSkillWithDetails(command),
                            HttpStatus.CREATED);
                }
            }
            catch (JwtException jwtException){
                return generateErrorResponse(jwtException.getMessage());
            }
            catch(SkillDomainException | JsonParseException e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create restaurant");
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