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

	            int i = 0;
	            while(ws.getWritableCell(i, kRow).getContents().isEmpty() == false){ 	//没问题了？
	            	i++;
	            }
	            //获得单元格对象 
	            jxl.write.WritableCell wc = ws.getWritableCell(i,kRow); 
	  
	            //判断单元格的类型, 做出相应的转化   
	            System.out.println(wc.getType().toString());
	            if (wc.getType() == CellType.EMPTY)    //没进来？？？？
	  
	            {   
	  
	            	
	                Label l = (Label) wc;   
	  
	                l.setString(indicator);   

	  
	            }   
	  
	            //写入Excel对象   
	  
	            wwb.write();   
	  
//	            wwb.close();   //这块儿？
		}catch(Exception e){
			e.printStackTrace();
		}
	            
	}
}
