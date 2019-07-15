package net.marklogic.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HtmlToPDFReport {

	/*public void htmltopdf(){
	FileInputStream fis = null;
	try   
	{  
	   fis = new FileInputStream("D://Html//junitnoframes.html");  
	}  
	catch (java.io.FileNotFoundException e)   
	{  
	    System.out.println("File not found: ");  
	}  
	    Tidy tidy = new Tidy(); 
	    tidy.setShowWarnings(false);
	    tidy.setXmlTags(false);
	    tidy.setInputEncoding("UTF-8");
	    tidy.setOutputEncoding("UTF-8");
	    tidy.setXHTML(true);// 
	    tidy.setMakeClean(true);
	    org.w3c.dom.Document xmlDoc = tidy.parseDOM(fis, null);  
	try  
	{  
	    tidy.pprint(xmlDoc,new FileOutputStream("D://Html//msc.xhtml"));  
	}  
	catch(Exception e)  
	{  
	}*/
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, DocumentException{
		// step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F:\\PDF_Report\\Report.pdf"));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream("F:\\Projects\\MedAvante_Automation\\Medavante-Automation\\Result\\TestSuite_87553\\CustomReport.html")); 
        //step 5
         document.close();
    }
}
