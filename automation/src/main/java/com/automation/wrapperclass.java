package com.automation;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.Assert;



public class wrapperclass 
{
	public static final String qa_url="https://opensource-demo.orangehrmlive.com/";//url donde se ejecutara el script 
	public static final String chrome_driver="webdriver.chrome.driver"; //driver chrome
	public static final String path_chrome_driver="./src/test/resources/Drivers/Chrome/chromedriver.exe"; //direccion del drive de chrome
	public static final String PATH_JSON_DATA = "./src/test/resources/testdata/json/";
	public static final String PATH_EXCEL_DATA = "./src/test/resources/testdata/Test.xls";
	public static String PATH_SCREENSHOTS = "./test-output/screenshot/";
	
	WebDriver driver;
	
	public wrapperclass (int navegador)
	{
		switch (navegador)
		{
			case 1:
				chromedriverconnection();
				launchbrowser();
				break;
		
		}
		
	}
	
	public void chromedriverconnection()
	{
		System.setProperty(chrome_driver,path_chrome_driver);
		driver= new ChromeDriver();
		
	}
	
	public void launchbrowser()
	{
		driver.get(qa_url);
		driver.manage().window().maximize();
		espera(5);
	}
	
	public void espera(int seg)
	{
		driver.manage().timeouts().implicitlyWait(seg, TimeUnit.SECONDS);
	}
	
	public void closebrowser()
	{
		try
		{
			driver.close();
		}
		catch(NoSuchSessionException e)	
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	public void left_click(String elemento, int id)
	{
		switch (id)
		{
		case 1://buscando por xpath
			driver.findElement(By.xpath(elemento)).click();
			espera(1);
			break;
		case 2://buscando por nombre
			driver.findElement(By.name(elemento)).click();
			espera(1);
			break;
		case 3://buscando por css
			driver.findElement(By.cssSelector(elemento)).click();
			espera(1);
			break;
		case 4://buscando por css
			driver.findElement(By.id(elemento)).click();
			espera(1);
			break;
		}
		
	}
	
	public void send_key(String elemento, int id,String msg)
	{
		switch (id)
		{
		case 1://buscando por xpath
			driver.findElement(By.xpath(elemento)).sendKeys(msg);
			espera(1);
			break;
		case 2://buscando por nombre
			driver.findElement(By.name(elemento)).sendKeys(msg);
			espera(1);
			break;
		case 3://buscando por css
			driver.findElement(By.cssSelector(elemento)).sendKeys(msg);
			espera(1);
			break;
		}
		
	}
	
	public String fecha()
    {
        String fec=null;
        Calendar fecha=new GregorianCalendar();
        String mes=Integer.toString(fecha.get(Calendar.MONTH)+1);
        String dia=Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
        String hora=Integer.toString(fecha.get(Calendar.HOUR));
        String min=Integer.toString(fecha.get(Calendar.MINUTE));
        String seg=Integer.toString(fecha.get(Calendar.SECOND));
        if((mes.length())<2)
        {
            mes="0"+mes;
        }
        if((dia.length())<2)
        {
            dia="0"+dia;
        }
        if((hora.length())<2)
        {
            hora="0"+hora;
        }
        if((min.length())<2)
        {
            min="0"+min;
        }
        if((seg.length())<2)
        {
            seg="0"+seg;
        }
        if((fecha.get(Calendar.AM_PM))==1)
        {
            fec=(fecha.get(Calendar.YEAR))+"-"+mes+"-"+dia+" "+hora+":"+min+":"+seg+"P.M.";
        }
        else
        {
            fec=(fecha.get(Calendar.YEAR))+"-"+mes+"-"+dia+" "+hora+":"+min+":"+seg+"A.M.";
        }
        
                
        return fec;
    }
	
	public void captura(String TC)
    {
        BufferedImage pantalla=null;
        {
            try 
            {
                Calendar fecha=new GregorianCalendar();
                String nombre=(fecha.get(Calendar.YEAR))+(fecha.get(Calendar.MONTH)+1)+fecha.get(Calendar.DAY_OF_MONTH)+fecha.get(Calendar.HOUR)+fecha.get(Calendar.MINUTE)+".png";
                PATH_SCREENSHOTS="C:\\Auto\\"+nombre;
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
                Rectangle screenRectangle = new Rectangle(screenSize);
                Robot robot = new Robot(); 
                BufferedImage image = robot.createScreenCapture(screenRectangle); 
                ImageIO.write(image, "png", new File(PATH_SCREENSHOTS)); 
                
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
	
	public Object encabezados(int columna)
    {
        InputStream excelStream = null;//inicia varible que apunta a archivo
        int filas=0;
        int columnas=0;
        int auxf=0;//auxiliar cuenta filas
        int auxc=0;//auxiliar cuenta columnas
        try 
        {
                excelStream = new FileInputStream(PATH_EXCEL_DATA);//carga/apunta archivo
                HSSFWorkbook hoja = new HSSFWorkbook(excelStream);//control del documento
                HSSFSheet registro = hoja.getSheetAt(0);//toma hoja 0
                HSSFRow fil;
                HSSFCell col; 
                filas=registro.getLastRowNum();//obtiene numero de registros
                fil=registro.getRow(auxf);//carga la variable fil con el un registro
                columnas=fil.getLastCellNum();//cuenta el numero de datos del registro
                col=registro.getRow(auxf).getCell(auxc);//llena el datos con el dato segun la iteracion
                hoja.close();
                    
                    
                //System.out.println("El dato es " +(col=registro.getRow(auxff).getCell(auxfc)));
                return (col=registro.getRow(auxf).getCell(columna));
        }   
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
        
    }
	
	public String consultar_dato(int fila,int columna,int caso)
    {
        InputStream excelStream = null;//inicia varible que apunta a archivo
        //String ruta = "C:\\Automatizacion\\Pool.xls";//ruta archivo
        int filas=0;
        int columnas=0;
        int auxf=0;//auxiliar cuenta filas
        int auxc=0;//auxiliar cuenta columnas
        String aux="";
        
        try 
        {
                excelStream = new FileInputStream(PATH_EXCEL_DATA);//carga/apunta archivo
                HSSFWorkbook hoja = new HSSFWorkbook(excelStream);//control del documento
                HSSFSheet registro = hoja.getSheetAt(caso);//toma hoja 0
                HSSFRow fil;
                HSSFCell col; 
                filas=registro.getLastRowNum();//obtiene numero de registros
                fil=registro.getRow(auxf);//carga la variable fil con el un registro
                columnas=fil.getLastCellNum();//cuenta el numero de datos del registro
                
                //System.out.println("El dato es " +(col=registro.getRow(auxff).getCell(auxfc)));
                aux=(col=registro.getRow(fila).getCell(columna)).toString();
                return aux;
        }   
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
	
	public int num_col(int caso) throws FileNotFoundException, IOException
    {
        int filas;
        HSSFRow fil;
        int columnas=0;
        int aux=0;
        InputStream excelStream = null;//inicia varible que apunta a archivo
        excelStream = new FileInputStream(PATH_EXCEL_DATA);//carga/apunta archivo
        HSSFWorkbook hoja = new HSSFWorkbook(excelStream);//control del documento
        HSSFSheet registro = hoja.getSheetAt(caso);//toma hoja 0
        filas=registro.getLastRowNum();//obtiene numero de registros
        fil=registro.getRow(aux);//carga la variable fil con el un registro
        columnas=fil.getLastCellNum();//cuenta el numero de datos del registro
        return columnas;
    }
	
	public int num_reg(int caso) throws FileNotFoundException, IOException
    {
        int filas;
        InputStream excelStream = null;//inicia varible que apunta a archivo
        excelStream = new FileInputStream(PATH_EXCEL_DATA);//carga/apunta archivo
        HSSFWorkbook hoja = new HSSFWorkbook(excelStream);//control del documento
        HSSFSheet registro = hoja.getSheetAt(caso);//toma hoja de ncaso
        filas=registro.getLastRowNum();//obtiene numero de registros
        return filas;
    }
	
	public int idtoint(String id)
	{
		int aux=1;
		if(id=="xpath")
		{
			aux=1;
		}
		else if(id=="nombre")
		{
			aux=2;
		}
		else if(id=="css")
		{
			aux=3;
		}
		else if(id=="id")
		{
			aux=4;
		}
		return aux;
	}
	
	public void ejecutar()
	{
		try
		{
			int aux=1;
			int caso=0;
			while(aux<=num_reg(0))
			{
				
				if((consultar_dato(aux, 0,caso)).equals("ingresar"))
				{
					
					send_key((consultar_dato(aux, 1,caso)).toString(),idtoint((consultar_dato(aux, 1,caso)).toString()),(consultar_dato(aux, 3,caso)).toString());
				}
				else if((consultar_dato(aux, 0,caso)).equals("click"))
				{
					left_click((consultar_dato(aux, 1,caso)).toString(),idtoint((consultar_dato(aux, 1,caso)).toString()));
				}
				else if((consultar_dato(aux, 0,caso)).equals("espera"))
				{
					double x=Double.valueOf((consultar_dato(aux, 3,caso)));
					espera((int)x);
					System.out.println("espera"+(int)x);
				}
				else
				{
					System.out.println("no encontro elemento");
				}
				System.out.println("fin r"+aux);
				aux++;
			}
			driver.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

}
