package fuck.it.attack.core.text;

import fuck.it.attack.core.Logger;
import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class TextFormat {

	public static List<Token> parse(String text) {
		List<Token> tokens = new ArrayList<>();
		String token = "";
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch(c) {
				// special escape sequence
				case '#': {
					if(text.length() <= i + 1 || text.charAt(i + 1) != '[') {
						token += "#";
						continue;
					}
					int end = text.indexOf(']', i);
					if(end == -1) {
						throw new IllegalStateException("Missing closing ] for special escape sequence in label. `" + text + "` at position " + i + "");
					}
					String special = text.substring(i + 2, end);

					addText(tokens, token);
					token = "";

					tokens.add(new Token(TokenType.SPECIAL, special));
					i += special.length() + 2;
				} break;

				// tab
				case '\t': {

					addText(tokens, token);
					token = "";

					tokens.add(new Token(TokenType.TAB, null));
				} break;

				// newline
				case '\n': {

					addText(tokens, token);
					token = "";

					tokens.add(new Token(TokenType.NEW_LINE, null));
				} break;

				// space
				case ' ': {

					addText(tokens, token);
					token = "";

					tokens.add(new Token(TokenType.SPACE, null));
				} break;

				// any other char
				default: {
					token += c;
				}
			}
		}

		addText(tokens, token);

		return tokens;
	}

	private static void addText(List<Token> tokens, String text) {
		if(text != null && !text.trim().isEmpty()) {
			tokens.add(new Token(TokenType.TEXT, text));
		}
	}

}
