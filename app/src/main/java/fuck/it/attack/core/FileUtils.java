package fuck.it.attack.core;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

}
