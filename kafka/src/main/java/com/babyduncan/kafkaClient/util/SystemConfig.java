package com.babyduncan.kafkaClient.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 系统配置(内部使用)
 * 获取SystemConfig 主要是-D参数传递的KV
 *
 * @author adyliu (adyliu@sohu-inc.com)
 * @author guohaozhao ( guohaozhao@sohu-inc.com)
 * @since 2011-6-13
 */
public final class SystemConfig {

    private static SystemConfig instance = new SystemConfig();

    public static SystemConfig getInstance() {
        return instance;
    }

    private Properties config = new Properties();

    private SystemConfig() {
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, null);
        return value == null ? defaultValue : Boolean.valueOf(value).booleanValue();
    }

    public int getInt(String key, int defaultValue) {
        String value = getString(key, null);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }

    public String getString(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null) {
            value = config.getProperty(key, defaultValue);
        }
        return value;
    }

    private Properties loadAllProperties(String resourceName) throws IOException {
        ClassLoader clToUse = SystemConfig.class.getClassLoader();
        Properties properties = new Properties();
        Enumeration<URL> urls = clToUse.getResources(resourceName);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            InputStream is = null;
            try {
                URLConnection con = url.openConnection();
                con.setUseCaches(false);
                is = con.getInputStream();
                properties.load(is);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        return properties;
    }

}
