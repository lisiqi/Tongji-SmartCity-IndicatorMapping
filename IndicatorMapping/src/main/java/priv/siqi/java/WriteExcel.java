package priv.siqi.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.CellType;
import jxl.Workbook;
import jxl.write.Label;

public class WriteExcel {
	
	public void writeCell(jxl.write.WritableWorkbook wwb,int kRow,String indicator){
		try{
	            //读取第一张工作表   
	  
	            jxl.write.WritableSheet ws = wwb.getSheet(0);   
	            
	            //测试用
	            if(ws == null)
	            	System.out.println("!!!!!");

	            int i = 0;

	            while(ws.getWritableCell(i,kRow).getContents().isEmpty() == false){ 	//没问题了？
	            	i++;
	            }
	            
	            Label label = new Label(i,kRow,indicator); 
	            ws.addCell(label);
	  	  
	            //写入Excel对象   
	            wwb.write();   

		}catch(Exception e){
			e.printStackTrace();
		}
	            
	}
}
