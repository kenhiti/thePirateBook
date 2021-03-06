package com.optimusDev.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.optimusDev.domain.Conta;

public class TpbRestToken extends UsernamePasswordAuthenticationToken {
	
	private static final long serialVersionUID = 1L;	
	
	private Date timestamp;	
	
	public TpbRestToken(Object principal, TpbRestCredentials credentials, Date timestamp) {
		super(principal, credentials);
		this.timestamp = timestamp;		
	}
	
	public TpbRestToken(Object principal, TpbRestCredentials credentials, Date timestamp,Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.timestamp = timestamp;		
	}

	@Override
    public Object getPrincipal() {
		if(super.getPrincipal() instanceof Conta){
			return super.getPrincipal();
		}
        return (String)super.getPrincipal();
    }
	    
    @Override
    public TpbRestCredentials getCredentials() {
        return (TpbRestCredentials) super.getCredentials();
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
}
