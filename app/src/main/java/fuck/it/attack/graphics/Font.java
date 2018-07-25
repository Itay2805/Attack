package fuck.it.attack.graphics;

public class Font {

    private SpriteSheet sheet;
    private String charset;

    public Font(SpriteSheet sheet, String charset) {
        this.sheet = sheet;
        this.charset = charset;
    }

    private Sprite getCharSprite(int index) {
        int x = index % sheet.getCols();
        int y = index / sheet.getCols();
        return new Sprite(0, 0, 0, 0, sheet, x, y);
    }

    public SpriteSheet getSpriteSheet() {
        return sheet;
    }

    public Sprite getCharSprite(char c) {
        int index = charset.indexOf(c);
        // Logger.info(c, " - ", index);
        if(index == -1) {
        	if(c == '\u0000') {
		        throw new IndexOutOfBoundsException("Font does not support char '" + c + "'");
	        }else {
		        return getCharSprite('\u0000');
	        }
        }
        return getCharSprite(index);
    }

    public String getCharset() {
        return charset;
    }
}
