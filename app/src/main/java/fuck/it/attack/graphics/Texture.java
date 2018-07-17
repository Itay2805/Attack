package fuck.it.attack.graphics;

import static android.opengl.GLES30.*;

public class Texture {

	private int id;

	public Texture() {
		final int[] ids = {id};
		glGenTextures(1, ids, 0);
		id = ids[0];
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	void cleanUp() {
		final int[] ids = {id};
		unbind();
		glDeleteTextures(1, ids, 0);
	}
}
