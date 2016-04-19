package com.appcloud.vm.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils {

	public static void main(String args[]) throws DocumentException {
		File directory = new File("");//设定为当前文件夹 
		    try {
				System.out.println(directory.getCanonicalPath());
				System.out.println(directory.getAbsolutePath());//获取绝对路径 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//获取标准的路径 
		
		SAXReader reader = new SAXReader();  
		Document  document = reader.read(new File("/create-instance-conf.xml"));  
		Element rootElm = document.getRootElement();  
		Element root1Elm = rootElm.element("userlist");  
		List nodes = root1Elm.elements("item");  
		    for (Iterator it = nodes.iterator(); it.hasNext();) {  
		      Element elm = (Element) it.next();  
		      System.out.println("index:"+elm.attributeValue("index")+" level:"+elm.attributeValue("level")+" nickname:"+elm.attributeValue("nickname")+" country:"+elm.attributeValue("country")+" weiwang:"+elm.attributeValue("weiwang"));       
		   } 
		    try{  
		        Document  doc = reader.read(new File("create-instance-conf.xml"));  
		        List projects=doc.selectNodes("ReturnInfo/userlist/item");  
		        Iterator it=projects.iterator();  
		        while(it.hasNext()){  
		          Element elm=(Element)it.next();        
		          System.out.println("index:"+elm.attributeValue("index")+" level:"+elm.attributeValue("level")+" nickname:"+elm.attributeValue("nickname")+" country:"+elm.attributeValue("country")+" weiwang:"+elm.attributeValue("weiwang")); 
		        }  
		             
		    }  catch(Exception ex){  
		       ex.printStackTrace();  
		    }    
		} 
	
}
