package com.pttk.utils;

import com.pttk.model.user.Librarian1;
import com.pttk.model.user.LibrarianManager1;
import com.pttk.model.user.Member1;
import com.pttk.model.user.Reader1;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

public class AuthUtils {
    public static String toHashed(String pw){
        return BCrypt.hashpw(pw, BCrypt.gensalt(12));
    }
    
    public static boolean verifyPassword(String pw, String hashedPw) {
        return BCrypt.checkpw(pw, hashedPw);
    }
    
    public static boolean isRole(HttpServletRequest request, HttpServletResponse response, String role) throws IOException, ServletException{
        
        HttpSession session = request.getSession(false);
        Member1 mb = session == null ? null : (Member1) session.getAttribute("member");
        
        return (role.equalsIgnoreCase("reader") && mb instanceof Reader1) || 
                (role.equalsIgnoreCase("librarian") && mb instanceof Librarian1) ||
                (role.equalsIgnoreCase("manager") && mb instanceof LibrarianManager1);
    }
}
