package org.vietsearch.essme.filter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FireBaseTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if(!request.getMethod().equals("GET")) {
            String authenticationHeader = request.getHeader("Authorization");
            //checks if token is there
            if (authenticationHeader == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token!");

            FirebaseToken decodedToken = null;
            try {
                //Extracts token from header
                String token = authenticationHeader.split(" ")[1];
                //verifies token to firebase server
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                String uid = decodedToken.getUid();
                System.out.println(FirebaseAuth.getInstance().getUser(uid).getEmail());
                AuthenticatedRequest authenticatedRequest = new AuthenticatedRequest(request,uid);
                chain.doFilter(authenticatedRequest,response);
            } catch (FirebaseAuthException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error! " + e.toString());
            }

            //if token is invalid
            if (decodedToken == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token!");
            }
        }
        else chain.doFilter(new AuthenticatedRequest(request,null),response);
    }
}