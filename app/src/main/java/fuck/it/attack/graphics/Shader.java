package fuck.it.attack.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import fuck.it.attack.core.FileUtils;
import fuck.it.attack.core.Logger;

import static android.opengl.GLES30.*;

public class Shader {

	private int programId;

	private HashMap<String, Integer> uniforms = new HashMap<>();

	public Shader(String vertexPath, String fragmentPath) {
		String vertexSource = FileUtils.readFile(vertexPath);
		String fragmentSource = FileUtils.readFile(fragmentPath);

		int vertexShader = compile(vertexSource, ShaderType.VERTEX);
		int fragmentShader = compile(fragmentSource, ShaderType.FRAGMENT);

		programId = glCreateProgram();
		glAttachShader(programId, vertexShader);
		glAttachShader(programId, fragmentShader);

		glLinkProgram(programId);

		final int[] data = new int[2];
		glGetProgramiv(programId, GL_ACTIVE_UNIFORMS, data, 0);
		glGetProgramiv(programId, GL_ACTIVE_UNIFORM_MAX_LENGTH, data, 1);

		IntBuffer sizeBuffer = IntBuffer.allocate(1);
		IntBuffer typeBuffer = IntBuffer.allocate(1);

		for(int i=0; i < data[0]; i++) {
			String name = glGetActiveUniform(programId, i, sizeBuffer, typeBuffer);
			int location = glGetUniformLocation(programId, name);
			uniforms.put(name, location);
		}

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

	public void setFloat(String name, float v) {
		glUniform1f(uniforms.get(name), v);
	}

	public void setInt(String name, int v) {
		glUniform1i(uniforms.get(name), v);
	}

	public void setVec2(String name, Vector2f vector) {
		glUniform2f(uniforms.get(name), vector.x, vector.y);
	}

	public void setVec3(String name, Vector3f vector) {
		glUniform3f(uniforms.get(name), vector.x, vector.y, vector.z);
	}

	public void setVec4(String name, Vector4f vector) {
		glUniform4f(uniforms.get(name), vector.x, vector.y, vector.z, vector.w);
	}

	public void setMat4(String name, Matrix4f matrix) {
		FloatBuffer buffer = FloatBuffer.allocate(16);
		buffer.put(matrix.m00());
		buffer.put(matrix.m01());
		buffer.put(matrix.m02());
		buffer.put(matrix.m03());
		buffer.put(matrix.m10());
		buffer.put(matrix.m11());
		buffer.put(matrix.m12());
		buffer.put(matrix.m13());
		buffer.put(matrix.m20());
		buffer.put(matrix.m21());
		buffer.put(matrix.m22());
		buffer.put(matrix.m23());
		buffer.put(matrix.m30());
		buffer.put(matrix.m31());
		buffer.put(matrix.m32());
		buffer.put(matrix.m33());
		buffer.flip();
		
		glUniformMatrix4fv(uniforms.get(name), 1, false, buffer);
	}

	public void cleanUp() {
		stop();
		glDeleteProgram(programId);
	}

	private enum ShaderType {
		VERTEX,
		FRAGMENT
	}
}
