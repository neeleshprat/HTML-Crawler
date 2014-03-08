package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Scanner;
import java.lang.Object;

import org.apache.commons.io.IOUtils;

/*
 * This is a program that crawls the top alexa sites and saves it on your local pc at the specified path (remember to change the variable homepath if you want to use it)
 * You also need to adapt the AMOUNT variable if you want to change the amount of crawled sites
 */
public class HTMLReader {
	private static int AMOUNT = 10;
	private static int START = 15201;
	private static String homepath = "D:/BusinessInformatics/Large_Scale_Extraction/workshop/Many_HTML_files/100000_Alexa1/";
	
	public static String receiveHTML(String path) throws IOException {
		try {			
			URL url = new URL(path);
			Scanner scan = new Scanner(url.openStream());
			StringBuilder sb = new StringBuilder();
			while(scan.hasNextLine()){
				sb.append(scan.nextLine() + "\n");			
			}
			scan.close();
			return sb.toString();

		} 
		catch (IOException eIO){
			return "EXCEPTION";
		}
		catch (Exception e) {
			// TODO: handle exception
			return "EXCEPTION";
		}
	}
	
	public static String[] readURLs(String path, int amount) throws IOException {
		String[] result = new String[amount];
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		for(int i = 0, j = 0; i < 10000 && j < amount; ++i) {
			String[] row = br.readLine().split(",");
			String lineNo = row[0];
			if (Integer.parseInt(lineNo) >= START)
			{
				result[j] = row[1];
				j++;
			}
		}
		return result;
	}
	
	public static void saveHTML(String path, String HTML) throws IOException {
		FileWriter w = new FileWriter(path);
		w.write(HTML);
		w.close();
	}
	
	public static void main(String[] args) {
		try {
			long startTime = System.currentTimeMillis();
			String[] alexa = readURLs("alexa10001to20000.csv", AMOUNT);
			System.out.println(alexa.length);
			System.out.println("URLs read");
			for(int i = 0; i < AMOUNT; ++i) {
				try{
				String htmlString = receiveHTML("http://" + alexa[i]);
				
				if(htmlString != "EXCEPTION")
					saveHTML(homepath + alexa[i] + ".html", htmlString);
								
				}catch(IOException e) {
					e.printStackTrace();
					continue;
				}				
			}
			System.out.println("Done!");
			long stopTime = System.currentTimeMillis();
			System.out.println(stopTime - startTime);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}