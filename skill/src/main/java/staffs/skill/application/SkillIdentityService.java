package staffs.skill.application;

import example.common.application.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SkillIdentityService {
    private JwtTokenUtil jwtTokenUtil;

    public boolean isSpecifiedUser(String token, String id) throws JwtException{
        checkIfTokenHasExpired(token);
        String idFound = jwtTokenUtil.getClaimFromToken(token, UserDTO.ID);
        return idFound.equals(id);
    }

    public boolean isAdmin(String token) throws JwtException{
        checkIfTokenHasExpired(token);
        String roleFound = jwtTokenUtil.getClaimFromToken(token, UserDTO.ROLE);
        System.out.println (roleFound);
        return roleFound.contains("ADMIN");
    }

    public void checkIfTokenHasExpired(String token) throws JwtException {
        if (jwtTokenUtil.isTokenExpired(token)){
            throw new JwtException("Token has expired");
        }
    }

    public UserDTO getDetailsFromToken(String token){
        return new UserDTO(jwtTokenUtil.getClaimFromToken(token, UserDTO.ID),
                            jwtTokenUtil.getClaimFromToken(token, UserDTO.USERNAME),
                            jwtTokenUtil.getClaimFromToken(token, UserDTO.PASSWORD),
                            jwtTokenUtil.getClaimFromToken(token, UserDTO.ADDRESS),
                            jwtTokenUtil.getClaimFromToken(token, UserDTO.FIRST_NAME),
                            jwtTokenUtil.getClaimFromToken(token, UserDTO.SURNAME),
                            jwtTokenUtil.getClaimFromToken(token, UserDTO.ROLE)
        );
    }
}
