package com.pkg;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
public class TextReco {
	
	Tesseract ts;
	public TextReco() {
		ts = new Tesseract();
//		ts.setDatapath("");
		ts.setLanguage("eng");
		try {
			String text = ts.doOCR(getImage("images/img.png"));
			System.out.println(text);
		} catch (TesseractException e) {		
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
	
	private BufferedImage getImage(String imgPath) throws IOException {
		//	Read image
		Mat mat = Imgcodecs.imread(imgPath);
		
		//	Change image to gray scale
		Mat gray = new Mat();
		Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);
		
		//	Resize image
		Mat resized = new Mat();
		Size size = new Size(mat.width() * 1.9f, mat.height() * 1.9f);
		Imgproc.resize(gray, resized, size);;
		
		// convert to Buffered image
		MatOfByte mof = new MatOfByte();
		byte imageByte[];
		Imgcodecs.imencode(".png", resized, mof);
		imageByte = mof.toArray();
		
		return ImageIO.read(new ByteArrayInputStream(imageByte));
	}

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new TextReco(); 

	}

}
