package com.solution.developerManager.endpoint;

import com.solution.developerManager.response.JwtResponse;
import com.solution.developerManager.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationPoint {

    final JWTUtil jwtUtil;

    @RequestMapping(value = "/builder-jwt", method = RequestMethod.POST)
    public JwtResponse builderJWT(@RequestBody Map<String, Object> claims)
    {
        return new JwtResponse(jwtUtil.generateToken(claims));
    }
}
