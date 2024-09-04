package staffs.skill.api;

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
    private SkillQueryHandler skillQueryHandler;
    private SkillApplicationService skillApplicationService;

    // e.g. http://localhost:8080/skills/all
    @GetMapping(path="/all")
    public Iterable<BaseSkill> findAll() {
        return skillQueryHandler.getAllSkills();
    }

    // e.g. http://localhost:8080/skills/{skillId}
    @GetMapping(path="/{skillId}")
    public ResponseEntity<GetSkillResponse> findById(@PathVariable String skillId) {
        return skillQueryHandler.getSkill(skillId).map(
                        o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //e.g. http://localhost:8080/skills/skillDetails/a
    @GetMapping(path="/skillDetails/{skillId}")
    public ResponseEntity<GetSkillDetailResponse> findSkillDetailsBySkillId(@PathVariable String skillId) {
        return skillQueryHandler.getSkillDetailResponse(skillId).map(
                        o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // e.g. POST http://localhost:8080/skills
    /**
     {
     "skillName": "Java Programming",
     "category": "Programming Languages"
     }
     **/
    @PostMapping
    public ResponseEntity<String> createSkillWithDetails(@RequestBody CreateSkillCommand command) {
        try {
            return new ResponseEntity<>(skillApplicationService.createSkillWithDetails(command),
                    HttpStatus.CREATED);
        } catch (SkillDomainException | JsonParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create skill");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
}
