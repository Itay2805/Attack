package fuck.it.attack.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import static android.opengl.GLES30.*;

public class Texture {

	private int id;

	public Texture() {
		final int[] ids = {id};
		glGenTextures(1, ids, 0);
		id = ids[0];
		bind();
		setSamplerState();
		unbind();
	}

	public Texture(final Bitmap bitmap) {
		final int[] ids = {id};
		glGenTextures(1, ids, 0);
		id = ids[0];
		bind();

		GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

		setSamplerState();
		unbind();
	}

	public Texture(int width, int height) {
		final int[] ids = {id};
		glGenTextures(1, ids, 0);
		id = ids[0];
		bind();

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, null);
		setSamplerState();

		unbind();
	}

	public static Texture createTexture(String path) {
		Texture texture;

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;

		final Bitmap bitmap = BitmapFactory.decodeFile(path, options);

		texture = new Texture(bitmap);

		bitmap.recycle();
		return texture;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void cleanUp() {
		final int[] ids = {id};
		unbind();
		glDeleteTextures(1, ids, 0);
	}

	private void setSamplerState() {
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	}
}
