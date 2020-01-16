/**
 * @author  zzd E-mail: zzd_zzd@hust.edu.cn
 * @date 创建时间：2018年10月25日 上午10:23:43
 * @version 1.0
 * @parameter 
 * @return 
 */
package edu.hust.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * @author zzd
 *
 */
public class ExcelUtil {
	
	private static final String XLS="xls";
	private static final String XLSX="xlsx";	

	/**
	 * 忽略第一行表头，读取表格中所有数字内容
	 * @param path 文件地址
	 * @param sheetName sheet名字
	 * @return 数字内容
	 */
	public static double[][] readTable(String path, String sheetName){
		double[][] contents = null;
		try {
			InputStream ips=new FileInputStream(path);
	
			Workbook wb=null;
			if(path.endsWith(XLS)){
				wb=new HSSFWorkbook(ips);
			}else if(path.endsWith(XLSX)){
				wb=new XSSFWorkbook(ips);
			}
			
			Sheet sheet=wb.getSheet(sheetName);
			int rowNum = sheet.getLastRowNum() - sheet.getFirstRowNum();
			int columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
			contents = new double[rowNum][columnNum];
			int i = 0;
			for(Row row:sheet){
				if(i>0){
					// 忽略一行表头
					int j=0;
					for(Cell cell:row){
						CellType cellType=cell.getCellType();
						double data=Double.NaN;
						switch(cellType){
							case NUMERIC:data=cell.getNumericCellValue();break;					
							case STRING:  
							case FORMULA: 
							case BLANK:   
							case BOOLEAN: 
							case ERROR:   
							default:
								System.out.println("Wrong cell type: "+cellType.name()+
										" in "+path+"--"+sheetName+"  row:"+i+" column:"+j);
								break;
						}
						contents[i-1][j] = data;
						j++;
						// System.out.print(data+"\t");
					}
				}
				i++;
				// System.out.println();
			}

			ips.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return contents;
	}

	/**
	 * 写表
	 * @param path
	 * @param sheetName
	 * @param contents
	 */
	@SuppressWarnings("resource")
	public static boolean writeTable(String path, String sheetName, List<List<String>> contents){
		boolean success = false;
		File file = new File(path);
		File parentFile = file.getParentFile();
		if(!file.exists()){
			if (!parentFile.exists()) {
				parentFile.mkdirs();		
			}
			try {
				file.createNewFile();
				Workbook wb=null;
				if(path.endsWith(XLS)){
					wb=new HSSFWorkbook();
				}else if(path.endsWith(XLSX)){
					wb=new XSSFWorkbook();
				}else{
					try {
						throw new Exception("The file format is not legal! The Excel file format must be xls or xlsx!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						success = false;
						e.printStackTrace();
					}
				}			
				FileOutputStream out = new FileOutputStream(path);
				wb.write(out);
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				success = false;
				e1.printStackTrace();
			}
		}		
		
		InputStream ips;
		Workbook wb=null;
		Sheet sheet=null;
		try {
			ips=new FileInputStream(path);
			if(path.endsWith(XLS)){
				wb=new HSSFWorkbook(ips);
			}else if(path.endsWith(XLSX)){
				wb=new XSSFWorkbook(ips);
			}else{
				try {
					throw new Exception("The file format is not legal! The Excel file format must be xls or xlsx!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					success = false;
					e.printStackTrace();
				}
			}
			sheet=wb.getSheet(sheetName);
			if(sheet==null){
				sheet=wb.createSheet(sheetName);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}

		sheet.setDefaultColumnWidth(20);
		CellStyle style=wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		
		
		for(int i=0;i<contents.size();i++){
			Row row=sheet.createRow(i);
			List<String> content = contents.get(i);
			for(int j=0;j<content.size();j++){
				Cell cell=row.createCell(j);
				try{
					cell.setCellValue(Double.valueOf(content.get(j)));					
				}catch(Exception e){
					cell.setCellValue(content.get(j));					
				}
				cell.setCellStyle(style);				
			}
		}
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(path);
			wb.write(out);
			out.close();
			success = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}		
		
		return success;
	}

	/**
	 * 写表
	 * @param path
	 * @param sheetName
	 * @param contents
	 */
	@SuppressWarnings("resource")
	public static boolean writeTable(String path, String sheetName, String[][] contents){
		boolean success = false;
		File file = new File(path);
		File parentFile = file.getParentFile();
		if(!file.exists()){
			if (!parentFile.exists()) {
				parentFile.mkdirs();		
			}
			try {
				file.createNewFile();
				Workbook wb=null;
				if(path.endsWith(XLS)){
					wb=new HSSFWorkbook();
				}else if(path.endsWith(XLSX)){
					wb=new XSSFWorkbook();
				}else{
					try {
						throw new Exception("The file format is not legal! The Excel file format must be xls or xlsx!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						success = false;
						e.printStackTrace();
					}
				}			
				FileOutputStream out = new FileOutputStream(path);
				wb.write(out);
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				success = false;
				e1.printStackTrace();
			}
		}		
		
		InputStream ips;
		Workbook wb=null;
		Sheet sheet=null;
		try {
			ips=new FileInputStream(path);
			if(path.endsWith(XLS)){
				wb=new HSSFWorkbook(ips);
			}else if(path.endsWith(XLSX)){
				wb=new XSSFWorkbook(ips);
			}else{
				try {
					throw new Exception("The file format is not legal! The Excel file format must be xls or xlsx!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					success = false;
					e.printStackTrace();
				}
			}
			sheet=wb.getSheet(sheetName);
			if(sheet==null){
				sheet=wb.createSheet(sheetName);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}

		sheet.setDefaultColumnWidth(20);
		CellStyle style=wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		
		
		for(int i=0;i<contents.length;i++){
			Row row=sheet.createRow(i);
			for(int j=0;j<contents[i].length;j++){
				Cell cell=row.createCell(j);
				try{
					cell.setCellValue(Double.valueOf(contents[i][j]));					
				}catch(Exception e){
					cell.setCellValue(contents[i][j]);					
				}
				cell.setCellStyle(style);				
			}
		}
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(path);
			wb.write(out);
			out.close();
			success = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			success = false;
			e.printStackTrace();
		}		
		
		return success;
	}
	



}
