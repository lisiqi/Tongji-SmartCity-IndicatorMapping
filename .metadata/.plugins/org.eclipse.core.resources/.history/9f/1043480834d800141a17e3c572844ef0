package priv.siqi.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.CellType;
import jxl.Workbook;
import jxl.write.Label;

public class WriteExcel {
	
	public void writeCell(int kRow,String indicator){
		try{
		jxl.Workbook readwb = null; 
		InputStream instream = new FileInputStream("src/main/resources/最新数据库指标.xls");   
		readwb = Workbook.getWorkbook(instream);
		
		//利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄   	  
	    jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(   
	  
	                    "映射结果.xls"), readwb);   
	  
	            //读取第一张工作表   
	  
	            jxl.write.WritableSheet ws = wwb.getSheet(0);   

	            int i = 0;
	            while(ws.getWritableCell(i, kRow) != null){ 	
	            	i++;
	            }
	            //获得单元格对象 
	            jxl.write.WritableCell wc = ws.getWritableCell(kRow, i); 
	  
	            //判断单元格的类型, 做出相应的转化   
	            if (wc.getType() == CellType.LABEL)    
	  
	            {   
	  
	                Label l = (Label) wc;   
	  
	                l.setString(indicator);   
	  
	            }   
	  
	            //写入Excel对象   
	  
	            wwb.write();   
	  
	            wwb.close();   
		}catch(Exception e){
			e.printStackTrace();
		}
	            
	}
}
