package com.expedia.tools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.expedia.tools.exception.CompareToolsException;

public class ExpConfigManager {

	private Map<String, Properties> propertiesCache = null;

	// thread safe
	public ExpConfigManager() {
		propertiesCache = Collections
				.synchronizedMap(new HashMap<String, Properties>());
	}

	private void init() {
		
		//ResourceBundle re=ResourceBundle.getBundle("PriceComTool/config/");
		String configPath=this.getClass().getClassLoader().getResource("/").getPath()+"config";
		File filePath = new File(configPath);
		if (!filePath.exists()) {
			throw new CompareToolsException(
					"in current dir is not exist config");
		}

		File configFiles[] = filePath.listFiles(new ConDirFilter(
				".*\\.properties"));
		Exception ex = null;
		for (File f : configFiles) {
			if (f.isFile() && f.exists()) {
				Properties p = new Properties();
				InputStream inStream;
				try {
					inStream = new FileInputStream(f);
					p.load(inStream);
					propertiesCache.put(f.getName(), p);
				} catch (FileNotFoundException e1) {
					ex = e1;
				} catch (IOException e) {
					ex = e;
				}

			}
		}
		if (ex != null) {
			throw new CompareToolsException(ex.getMessage(), ex);
		}

	}

	public Object getValue(String fileName, String key){
		this.init();
		Object value = null;
		if (propertiesCache.containsKey(fileName)) {
			Properties p = propertiesCache.get(fileName);
			value = p.get(key);
		}
		return value;
	}

	private class ConDirFilter implements FilenameFilter {

		Pattern p = null;

		public ConDirFilter(String pattern) {
			p = Pattern.compile(pattern);
		}

		public boolean accept(File dir, String name) {

			return p.matcher(new File(name).getName()).matches();
		}

	}

}
