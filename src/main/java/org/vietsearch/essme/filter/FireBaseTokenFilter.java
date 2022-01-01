package org.vietsearch.essme.filter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vietsearch.essme.model.user.User;
import org.vietsearch.essme.repository.UserRepository;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FireBaseTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if(!request.getMethod().equals("GET")) {
            String authenticationHeader = request.getHeader("Authorization");
            //checks if token is there
            if (authenticationHeader == null)
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing token!");
            else {
                FirebaseToken decodedToken = null;
                try {
                    //Extracts token from header
                    String token = authenticationHeader.split(" ")[1];
                    //verifies token to firebase server
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                    //if token is invalid
                    if (decodedToken == null) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token!");
                    }
                    else {
                        String uid = decodedToken.getUid();
                        saveUser(uid);
                        AuthenticatedRequest authenticatedRequest = new AuthenticatedRequest(request, uid);
                        chain.doFilter(authenticatedRequest, response);
                    }
                } catch (FirebaseAuthException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error! " + e.toString());
                }
            }
        }
        else chain.doFilter(new AuthenticatedRequest(request,null),response);
    }

    private void saveUser(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        System.out.println(userRecord.getEmail());
        User user = new User();
        user.setUid(uid);
        user.setEmail(userRecord.getEmail());
        user.setDisplayName(userRecord.getDisplayName());
        user.setPhoneNumber(userRecord.getPhoneNumber());
        user.setPhotoURL(userRecord.getPhotoUrl());
        userRepository.save(user);
    }
}