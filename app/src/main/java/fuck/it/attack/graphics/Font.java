package fuck.it.attack.graphics;

public class Font {

    private SpriteSheet sheet;
    private Sprite[] chars;
    private String charset;
    private int size;

    public Font(SpriteSheet sheet, String charset, int size) {
        this.sheet = sheet;
        this.charset = charset;
        this.size = size;

        chars = new Sprite[charset.length()];
        for(int i = 0; i < charset.length(); i++) {
            chars[i] = getCharSprite(i);
        }
    }

    public Font(SpriteSheet sheet, String charset) {
        this(sheet, charset, SpriteSheet.SPRITE_SIZE_WIDTH);
    }

    private Sprite getCharSprite(int index) {
        int x = index % sheet.getCols();
        int y = (index - x) / sheet.getCols();
        return new Sprite(0, 0, size, size, sheet, x, y);
    }

    public SpriteSheet getSpriteSheet() {
        return sheet;
    }

    public Sprite getCharSprite(char c) {
        int index = charset.indexOf(c);
        if(index == -1) {
            // ?
            throw new IndexOutOfBoundsException("Font does not support char '" + c + "'");
        }
        return chars[index];
    }

    public int getSize() {
        return size;
    }

    public String getCharset() {
        return charset;
    }
}
