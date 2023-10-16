package br.com.oresteslucas.backend.filter;

import java.io.IOException;
import java.util.Base64;
import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.oresteslucas.backend.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath();

                if(servletPath.startsWith("/tasks")){

                    // 1 - Pegar a autenticação (usuário e senha)
                    var authorization = request.getHeader("Authorization");
                    var authEncoded = authorization.substring("Basic".length()).trim();
                    
                    byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

                    var authString = new String(authDecoded);

                    String[] credentials = authString.split(":");
                    String username = credentials[0];
                    String password = credentials[1];

                    // 2 - Validar usuário
                    var user = this.iUserRepository.findByUsername(username);
                    if (user == null){
                        response.sendError(401);
                    }else {
                        // 3 - Validar senha
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                        if(passwordVerify.verified){
                            // Segue para o controller
                            request.setAttribute("idUser", user.getId());
                            filterChain.doFilter(request, response);
                        }else{
                            response.sendError(401);
                        }

                    }
                
                } else{
                    filterChain.doFilter(request, response);
                }
                
    }

}