package com.optimusDev.security;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.codec.Base64;

import com.optimusDev.domain.Conta;
import com.optimusDev.repository.ContaRepository;

public class TpbRestAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private ContaRepository repository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException{
		TpbRestToken restToken = (TpbRestToken) authentication;
		
		String apiKey = restToken.getPrincipal().toString();		
		
		// hashed blob
		TpbRestCredentials credentials = restToken.getCredentials();

		// get secret access key from api key
		String secret = "";
		String hmac = null;
		
		Conta conta = repository.findByEmailEquals(apiKey);		
		
		if(conta != null){
			try {
				secret = conta.getSenha();
				hmac = calculateHMAC(secret, credentials.getRequestData());
				System.out.println("HMAC Sign : "+hmac);
			} catch (Exception e) {				
				throw new BadCredentialsException("Invalid username or password.");
			} 
		}
		else{
			throw new BadCredentialsException("Invalid username or password.");
		}			
		
		// calculate the hmac of content with secret key
		//String hmac = calculateHMAC(secret, credentials.getRequestData());
		// check if signatures match
		if (!credentials.getSignature().equals(hmac)) {
			throw new BadCredentialsException("Invalid username or password.");
		}

		// this constructor create a new fully authenticated token, with the
		// "authenticated" flag set to true
		// we use null as to indicates that the user has no authorities. you can
		// change it if you need to set some roles.
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMINISTRADOR"));
		
		restToken = new TpbRestToken(conta, credentials, restToken.getTimestamp(), authorities);

		return restToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TpbRestToken.class.equals(authentication);
	}

	private String calculateHMAC(String secret, String data) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			String result = new String(Base64.encode(rawHmac));
			return result;
		} catch (GeneralSecurityException e) {
			throw new IllegalArgumentException();
		}
	}

}
