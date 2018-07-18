package com.rchaudhari.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;

public class UTF8Util {

	static Map<Integer, String> utf8Map = null;

	public static void main(String args[]) throws IOException {
		System.out.println(getDescription('1'));
	}

	public static String getDescription(char c) {

		int c1 = (int) c;
		return _searchCharMap(c1);

	}

	private static String _searchCharMap(int codePoint) {

		try {
			_loadFile();
		} catch (IOException e) {
			System.err.println("UTF-8 Data file does not exist");
		}
		// String hex = Integer.toHexString(c1);
		// System.out.println(hex + "\t" + c1);
		return utf8Map.get(codePoint);

	}

	private static void _loadFile() throws IOException {

		if (utf8Map == null) {
			utf8Map = new HashMap<Integer, String>();
			CSVReader reader = new CSVReader(new FileReader(new File(
					"Data/UTF-8_Character.csv")));
			Pattern pat = Pattern.compile("\\((U\\+)([a-zA-Z\\+0-9]+)\\)");
			String data[] = null;
			while ((data = reader.readNext()) != null) {
				Matcher mat = pat.matcher(data[1]);
				String description = data[1];
				String key = null;
				if (mat.find()) {
					key = mat.group(2);
					if (key != null) {
						key = key.replace("U+", "");
						utf8Map.put(Integer.parseInt(key, 16), description);
					}
				}
			}
			reader.close();
		}

	}

}
