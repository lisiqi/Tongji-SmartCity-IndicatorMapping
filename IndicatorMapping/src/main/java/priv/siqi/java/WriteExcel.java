package priv.siqi.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

//import jxl.CellType;
//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class WriteExcel {
	WriteExcel(){
		
		//先把数据库指标拷贝到映射结果里
		try {
			InputStream inp = new FileInputStream("src/main/resources/最新数据库指标.xls");
			Workbook wb1 = WorkbookFactory.create(inp);
		    
		    Workbook wb = new HSSFWorkbook();
			wb = wb1;
			 
			FileOutputStream fileOut = new FileOutputStream("映射结果.xls");		    
		    wb.write(fileOut);
		    fileOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//在mapping里每循环一次都要打开关闭一次，是否影响效率？
	public void writeCell(int kRow,String indicator){
		try{
			//继续写映射结果
			InputStream inp = new FileInputStream("映射结果.xls");
			Workbook wb = WorkbookFactory.create(inp);
			
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.getRow(kRow);
			
			int i = 0;
            while(row.getCell(i) != null){ 	//没问题了？
            	i++;
            }
            Cell cell = row.createCell(i);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(indicator);
            
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream("映射结果.xls");
            wb.write(fileOut);
            fileOut.close();
            
//	            //读取第一张工作表   
//	  
//	            jxl.write.WritableSheet ws = wwb.getSheet(0);   
//	            
//	            //测试用
//	            if(ws == null)
//	            	System.out.println("!!!!!");
//
//	            int i = 0;
//
//	            while(ws.getWritableCell(i,kRow).getContents().isEmpty() == false){ 	//没问题了？
//	            	i++;
//	            }
//	            
//	            Label label = new Label(i,kRow,indicator); 
//	            ws.addCell(label);
//	  	  
//	            //写入Excel对象   
//	            wwb.write();   

		}catch(Exception e){
			e.printStackTrace();
		}
	            
	}
}
