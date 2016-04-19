package com.appcloud.vm.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesUtil {

	
	final static Pattern ENV_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");//要求是".*的一次或零次"的写法
    String filePath;

    /**
     * 得到property文件的配置,基础路径为src或者resource，他们编译后都在WEB下的classes中
     * 返回的Property会做如下转换：<br/>
     * 	1.property value中形如${key}的字符串会被替换为 System.getProperty("key")<br/>
     *  2.Property.getProperty(key)中，如果配置中未包含key，会得到IllegalStateException异常<br/>
     *  3.Property.getProperty(key, defaultValue)时，如果配置未包含key,将使用defaultValue，不会有异常<br/>
     * @param filePath
     * @return
     * @throws IOException 
     */
    public Properties getPropertyFileConfiguration(String filePath) {
        this.filePath = filePath;
        Properties properties = (Properties)new EnvWrappedProperties();
        try {
			properties.load(getClass().getClassLoader().getResourceAsStream(filePath));
		} catch (IOException e) {
			System.out.println("PropertiesUtil类getPropertyFileConfiguration方法错误：加载Properties失败");
			e.printStackTrace();
		}
        return properties;
    }

    class EnvWrappedProperties extends Properties {

        public EnvWrappedProperties() {
            super();
        }

        private String envString(String s) {
            String env;
            Matcher m = ENV_PATTERN.matcher(s);
            while (m.find()) {
                env = m.group(1);
                s = m.replaceFirst(System.getProperty(env).replace("\\", "\\\\").replace("$", "\\$"));
                m = m.reset(s);
            }
            return s;
        }

        @Override
        public String getProperty(String key) {
            String s = super.getProperty(key);
            if (s == null) {
                throw new IllegalStateException("初始化属性" + key + "失败, 请在"
                        + filePath
                        + "中增加该属性的声明或者在Constants.java中删除载入该属性，否则，请检查属性名是否拼写错误!");
            }
            return envString(s);
        }

        @Override
        public String getProperty(String key, String defaultValue) {
            String s = super.getProperty(key);
            if (s == null) {
                s = defaultValue;
            }
            return envString(s);
        }

		@Override
		public synchronized Object setProperty(String key, String value) {
			return super.setProperty(key, value);
		}
        
        
    }
    
    public static void main(String[] args) {
      Properties p = new PropertiesUtil().getPropertyFileConfiguration("create-instance-conf.properties");
      System.out.println(p.getProperty("CompareResultEntity"));
	}
    
}
