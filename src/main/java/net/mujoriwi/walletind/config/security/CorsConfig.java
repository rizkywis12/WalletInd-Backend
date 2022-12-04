package net.mujoriwi.walletind.config.security;


import net.mujoriwi.walletind.config.jwt.JwtUtil;
import net.mujoriwi.walletind.service.serviceimpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// cors - cross origin resource sharing, suatu resource dapat diakses dari url lainnya
// jwt sebagai filternya
@Component
public class CorsConfig extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserDetailsServiceImpl userdDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String headerAuth = request.getHeader("Authorization");

    // check header is null or not
    if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
      String jwtToken = headerAuth.substring(7, headerAuth.length());

      if (jwtToken != null && jwtUtil.validateJwtToken(jwtToken)) {
        String username = jwtUtil.getUsernameFromToken(jwtToken);

        // dibentuk user detailsnya
        UserDetails userDetails = userdDetailsService.loadUserByUsername(username);

        // authenticate the user
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Cache-Control");
    response.setHeader("Access-Control-Max-Age", "3600");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(200);
    } else {
      filterChain.doFilter(request, response);
    }

  }

}
