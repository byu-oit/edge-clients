package edu.byu.edge.accesstoken.domain;

/**
 * Created by Scott Hutchings on 6/28/2016.
 */
public class AccessToken {
	private String token;
	private TokenType type;
	private long expires;
	private long refreshTime;

	public AccessToken() {
	}

	public AccessToken(TokenType type) {
		this.type = type;
	}

	public AccessToken(String token, TokenType type, long expires, long refreshTime) {
		this.token = token;
		this.type = type;
		this.expires = expires;
		this.refreshTime = refreshTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}
}
