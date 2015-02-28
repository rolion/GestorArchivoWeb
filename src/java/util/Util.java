/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Rodry
 */
public class Util {
    
    public static File InputStreamToFile(InputStream in){
        File f=new File(System.getProperty("user.dir")+"\\myTemplate.jpg");
        
	OutputStream outputStream = null;
 
	try {
		// read this file into InputStream
		//inputStream = new FileInputStream("/Users/mkyong/Downloads/holder.js");
 
		// write the inputStream to a FileOutputStream
		outputStream = 
                    new FileOutputStream(f);
 
		int read = 0;
		byte[] bytes = new byte[in.available()];
 
		while ((read = in.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
 
		System.out.println("Done!");
 
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				// outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		}
	}
        return f;
    }
    
}
