package com.expedia.tools.util.test;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;



public class Test {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		String st = "/Flights-SearchResults-RoundTrip?c=2a2156e5-e6cc-4a73-9e2f-d94acdb7bb3a&";
//		String uc[] = st.split("\\?");
//		// System.out.println(uc[0]+" "+uc[1]);
//
//		String qsrc = "qscr=flpg%26rurl_rurl=qscr%253Dmamp%26piid=d65e07ff4133c793cf3709871fa6ea0e%26";
//		qsrc = qsrc.substring(0, qsrc.lastIndexOf("%"));
//
//        qsrc = qsrc.replaceAll("%", "%25");
//
//		qsrc = qsrc.replace("=", "%3D");
//
//		System.out.println("uc...." + qsrc);
		
		public static void main(String[] args) throws MalformedURLException,    
        IOException, URISyntaxException, AWTException {    
    //此方法仅适用于JdK1.6及以上版本    
    Desktop.getDesktop().browse(    
            new URL("http://google.com/intl/en/").toURI());    
    Robot robot = new Robot();    
    robot.delay(10000);    
    Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());    
    int width = (int) d.getWidth();    
    int height = (int) d.getHeight();    
    //最大化浏览器    
    robot.keyRelease(KeyEvent.VK_F11);    
    robot.delay(2000);    
    Image image = robot.createScreenCapture(new Rectangle(0, 0, width,    
            height));    
    BufferedImage bi = new BufferedImage(width, height,    
            BufferedImage.TYPE_INT_RGB);    
    Graphics g = bi.createGraphics();    
    g.drawImage(image, 0, 0, width, height, null);    
    //保存图片    
    ImageIO.write(bi, "jpg", new File("google.jpg"));    
} 
	}
	



