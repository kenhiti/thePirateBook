package com.optimusDev.security;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

public class TpbRestSecurityFilter extends GenericFilterBean {
	
	/*@Autowired
	private UsuarioRepository repository;*/
		
	// Enable Multi-Read for PUT and POST requests
    private static final TreeSet<String> METHOD_HAS_CONTENT = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER) {
        private static final long serialVersionUID = 1L; 
        { add("PUT"); add("POST"); }
    };
    
    private AuthenticationManager authenticationManager;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private Md5PasswordEncoder md5;
    

    public TpbRestSecurityFilter(AuthenticationManager authenticationManager) {
        this(authenticationManager, new TpbRestAuthenticationEntryPoint());
        //((AgoRestAuthenticationEntryPoint)this.authenticationEntryPoint);
    }

    public TpbRestSecurityFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.md5 = new Md5PasswordEncoder();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // use wrapper to read multiple times the content
       
    	TpbAuthenticationRequestWrapper request = new TpbAuthenticationRequestWrapper((HttpServletRequest) req);
		
        HttpServletResponse response = (HttpServletResponse) resp;
        
        // Get authorization header
        String credentials = request.getHeader("Authorization");

        // If there's not credentials return...
        if (credentials == null) {
            chain.doFilter(request, response);
            return;
        }

        // Authorization header is in the form <public_access_key>:<signature>
	        String auth[] = credentials.split(":");
        
        // get md5 content and content-type if the request is POST or PUT method
        boolean hasContent = METHOD_HAS_CONTENT.contains(request.getMethod());
        
        String contentMd5 = hasContent ? md5.encodePassword(request.getPayload(), null) : "";
        
        String contentType = hasContent ? request.getContentType() : "";
        contentType = contentType.toLowerCase();
        
        if(contentType.contains ("charset=utf-8")){
        	contentType = contentType.substring(0, 16);
        }
        
        if(contentType.contains ("multipart/form-data")){
        	contentType = "application/json";
        	contentMd5 = md5.encodePassword("{}", null);
        }
        
        String timestamp = request.getHeader("x-date");
                
        // calculate content to sign
        StringBuilder toSign = new StringBuilder();
        toSign.append(request.getMethod()).append("\n")
              .append(contentMd5).append("\n")
              .append(contentType).append("\n")
              .append(timestamp).append("\n")
              .append(request.getRequestURI());
        
        // a rest credential is composed by request data to sign and the signature
        TpbRestCredentials restCredential = new TpbRestCredentials(toSign.toString(), auth[1]);

        // calculate UTC time from timestamp (usually Date header is GMT but still...)
        
        long milliSeconds = Long.parseLong(timestamp);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Date date = null;
        
        try {
            date = calendar.getTime();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        
        // Create an authentication token
        /*Usuario usuario = repository.findByEmailEquals(auth[0]);*/
        
        Authentication authentication = new TpbRestToken(auth[0], restCredential, date);

        try {
            // Request the authentication manager to authenticate the token (throws exception)
            Authentication successfulAuthentication = authenticationManager.authenticate(authentication);
            
            // Pass the successful token to the SecurityHolder where it can be
            // retrieved by this thread at any stage.
            SecurityContextHolder.getContext().setAuthentication(successfulAuthentication);
            // Continue with the Filters
            chain.doFilter(request, response);
        } catch (org.springframework.security.core.AuthenticationException authenticationException) {
            // If it fails clear this threads context and kick off the
            // authentication entry point process.
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, authenticationException);
        }
    }

}
