package staffs.skill.api;

import io.jsonwebtoken.JwtException;
import staffs.skill.application.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import staffs.skill.domain.Category;
import staffs.skill.infrastructure.Skill;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/skills")
public class SkillController {
    private final SkillIdentityService identityService;
    private SkillQueryHandler skillQueryHandler;
    private SkillApplicationService skillApplicationService;

    // e.g. http://localhost:8080/skills/all
    @GetMapping(path = "/all")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token) {
        try {
            if (identityService.isAdmin(token)) {
                return ResponseEntity.ok().body(skillQueryHandler.getAllSkills());
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());

        } catch (IllegalArgumentException iae) {
        }
        return generateErrorResponse("user not authorised");
    }

    // e.g. http://localhost:8080/skill/categories
    @GetMapping(path = "/categories")
    public ResponseEntity<?> findAllCategories(@RequestHeader("Authorization") String token) {
        try {
            if (identityService.isAdmin(token)) {
                return ResponseEntity.ok().body(skillQueryHandler.getAllCategories());
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());

        } catch (IllegalArgumentException iae) {
        }
        return generateErrorResponse("user not authorised");
    }


    // e.g. http://localhost:8080/skills/s1
    @GetMapping(path = "/{skillId}")
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
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (IllegalArgumentException iae) {
        }
        return generateErrorResponse("user not authorised");
    }

    // view all skills in a category e.g. http://localhost:8080/skills/category/{categoryId}
    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<?> getSkillsByCategory(@PathVariable String categoryId,
                                             @RequestHeader("Authorization") String token) {
        try {
            if (identityService.isAdmin(token) || identityService.isSpecifiedUser(token, String.valueOf(categoryId))) {
                List<Skill> skillList = skillQueryHandler.getSkillsByCategory(String.valueOf(categoryId));
                return ResponseEntity.ok(skillList);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        }
        return generateErrorResponse("user not authorised");
    }

    //e.g. http://localhost:8080/skills/skillDetails/s1
    @GetMapping(path = "/skillDetails/{skillId}")
    public ResponseEntity<?> findSkillDetailBySkillId(@PathVariable String skillId,
                                                      @RequestHeader("Authorization") String token) {
        try {
            //Valid user or ADMIN?
            if (identityService.isAdmin(token) ||
                    identityService.isSpecifiedUser(token, skillId)) {
                return skillQueryHandler.getSkillDetailResponse(skillId).map(
                                o -> new ResponseEntity<>(o, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (IllegalArgumentException iae) {
        }
        return generateErrorResponse("user not authorised");
    }

    // e.g. POST http://localhost:8080/skills

    /**
     {
     "skillName": "New Skill Name",
     "category": "c1",
     "skillDetail": [
     {
     "skillName": "java",
     "proficiencyLevel": "strong"
     }
     ]
     }

     **/
    @PostMapping
    public ResponseEntity<?> createSkillWithDetails(@RequestBody CreateSkillCommand command,
                                                    @RequestHeader("Authorization") String token) {
        try {
            //Valid ADMIN?
            if (identityService.isAdmin(token)) {
                return new ResponseEntity<>(skillApplicationService.createSkillWithDetails(command),
                        HttpStatus.CREATED);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (SkillDomainException | JsonParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create skill");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
        return generateErrorResponse("user not authorised");
    }



    @PutMapping("/{skillId}")
    public ResponseEntity<?> updateSkillWithDetails(@PathVariable String skillId,
                                                    @RequestBody CreateSkillCommand command,
                                                    @RequestHeader("Authorization") String token) {
        try {
            // Valid ADMIN?
            if (identityService.isAdmin(token)) {
                skillApplicationService.updateSkillWithDetails(skillId, command);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (SkillDomainException | JsonParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update skill");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
        return generateErrorResponse("User not authorized");
    }

    private ResponseEntity<?> generateErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }



    // e.g. DELETE http://localhost:8080/skills/s1
    @DeleteMapping("/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable String skillId,
                                         @RequestHeader("Authorization") String token) {
        try {
            // Valid ADMIN?
            if (identityService.isAdmin(token)) {
                skillApplicationService.removeSkill(skillId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (JwtException jwtException) {
            return generateErrorResponse(jwtException.getMessage());
        } catch (SkillDomainException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
        return generateErrorResponse("User not authorized");
    }

}
