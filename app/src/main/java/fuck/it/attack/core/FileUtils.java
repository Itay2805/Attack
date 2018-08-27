package fuck.it.attack.core;

import android.content.res.AssetManager;

import java.io.*;

public class FileUtils {

	public static AssetManager assetManager;

	public static String readFile(String path) {
		StringBuilder result = new StringBuilder();

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(assetManager.open(path)));
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}
		} catch (IOException e) {
			Logger.error("Could not open file \"" + path + "\"");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Logger.error("Could not close file \"" + path + "\"");
				}
			}
		}
		return result.toString();
	}

	public static byte[] readFileBytes(String path) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream is = null;

		try {
			is = assetManager.open(path);

			byte[] data = new byte[16384];
			int nRead;
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();

		} catch (IOException e) {
			Logger.error("Could not open file \"" + path + "\"");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Logger.error("Could not close file \"" + path + "\"");
				}
			}
		}

		return buffer.toByteArray();
	}

}
