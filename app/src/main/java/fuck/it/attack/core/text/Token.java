package fuck.it.attack.core.text;

import fuck.it.attack.graphics.Color;

public class Token {

	private TokenType type;
	private String token;

	public Token(TokenType type, String token) {
		this.type = type;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public TokenType getType() {
		return type;
	}

	@Override
	public String toString() {
		String str = "Token [type: " + type;
		if(token != null) {
			str += ", token: " + token;
		}
		str += "]";
		return str;
	}
}
