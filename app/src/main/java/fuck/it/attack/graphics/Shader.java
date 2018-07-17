package fuck.it.attack.graphics;

import fuck.it.attack.core.FileUtils;
import fuck.it.attack.core.Logger;

import static android.opengl.GLES30.*;

public class Shader {

	private int programId;

	public Shader(String vertexPath, String fragmentPath) {
		String vertexSource = FileUtils.readFile(vertexPath);
		String fragmentSource = FileUtils.readFile(fragmentPath);

		int vertexShader = compile(vertexSource, ShaderType.VERTEX);
		int fragmentShader = compile(fragmentSource, ShaderType.FRAGMENT);

		programId = glCreateProgram();
		glAttachShader(programId, vertexShader);
		glAttachShader(programId, fragmentShader);

		glLinkProgram(programId);

		glDetachShader(programId, vertexShader);
		glDetachShader(programId, fragmentShader);

		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}

	private int compile(String source, ShaderType type) {
		int shaderId;

		shaderId = glCreateShader(type == ShaderType.VERTEX ? GL_VERTEX_SHADER : GL_FRAGMENT_SHADER);

		glShaderSource(shaderId, source);
		glCompileShader(shaderId);

		final int[] compiled = new int[1];
		glGetShaderiv(shaderId, GL_COMPILE_STATUS, compiled, 0);

		if (compiled[0] == GL_FALSE) {
			Logger.error("Could not compile " + (type == ShaderType.VERTEX ? "vertex" : "fragment") + " shader!");
			Logger.error(glGetShaderInfoLog(shaderId));

			glDeleteShader(shaderId);
			return -1;
		}

		return shaderId;
	}

	public void start() {
		glUseProgram(programId);
	}

	public void stop() {
		glUseProgram(0);
	}

	private enum ShaderType {
		VERTEX,
		FRAGMENT
	}
}
