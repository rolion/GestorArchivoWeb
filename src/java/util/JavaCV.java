/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_32F;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvMinMaxLoc;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_core.cvSize;
import static org.bytedeco.javacpp.opencv_core.cvZero;
import static org.bytedeco.javacpp.opencv_imgproc.CV_TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvMatchTemplate;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;

/**
 *
 * @author Rodry
 */
public class JavaCV {
    
    
    public static boolean mathTemplate(IplImage src,IplImage tmp){
        boolean rspt = false;
        IplImage result=null; 
        try{
            
            result  = cvCreateImage(cvSize(src.width()-tmp.width()+1, src.height()-tmp.height()+1), IPL_DEPTH_32F, 1);
            cvZero(result);
            //Match Template Function from OpenCV
            cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);
            // CV_TM_CCORR_NORMED CV_TM_SQDIFF_NORMED 
            DoublePointer min_val = new DoublePointer(2);
            DoublePointer max_val =  new DoublePointer(2);
            CvPoint minLoc = new CvPoint();
            CvPoint maxLoc = new CvPoint();
            cvMinMaxLoc(result, min_val, max_val, minLoc, maxLoc,null);
            if(max_val.get()>=0.99)
                rspt=true;
        }catch(Exception e){
                System.out.println("Error:"+e.getMessage());
        }finally{
                cvReleaseImage(src);
                cvReleaseImage(tmp);
                if(result!=null)
                    cvReleaseImage(result);
        }
        
        return rspt;
    }
    
}
