package priv.siqi.java;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.huaban.analysis.jieba.JiebaSegmenter;

public class SegmentDBIndicator {
	private ArrayList<ArrayList<String>> dbArr;
	
	public ArrayList<ArrayList<String>> getDbArr(){
		return dbArr;
	}
	
//	public void addDbArr(ArrayList<String> tepArr){
//		dbArr.add(tepArr);
//	}
	
	SegmentDBIndicator(){
		HashSet<String> hashset = new HashSet<String>();
		
		
		jxl.Workbook readwb = null;

		try{
			InputStream instream = new FileInputStream("src/main/resources/最新数据库指标.xls");   

            readwb = Workbook.getWorkbook(instream);   
          //Sheet的下标是从0开始   
            
            //获取第一张Sheet表     
            Sheet readsheet = readwb.getSheet(0);   
  
            //获取Sheet表中所包含的总列数     
            int rsColumns = readsheet.getColumns();   
  
            //获取Sheet表中所包含的总行数    
            int rsRows = readsheet.getRows();      
            
            ArrayList<String> tepArr = new ArrayList<String>();
            
            dbArr = new ArrayList();
            
            //获取指定单元格的对象引用     
            for (int i = 0; i < rsRows; i++)   
            {    
                for (int j = 0; j < rsColumns; j++)   
                {   
                	Cell cell = readsheet.getCell(j, i); 
                    String indicator = new String(cell.getContents());
                    
                    JiebaSegmenter segmenter = new JiebaSegmenter();                            
                    List<String> strings = segmenter.sentenceProcess(indicator);//一长串的空格也在strings里面
                    for(String s : strings){
                    	if(s.matches(" *") == false){ //用regular expression 去掉
                    		hashset.add(s);
                    	}	
                    }
                    tepArr.addAll(hashset);
                    dbArr.add(tepArr); //把分词后的结果存到dbArr里！  //..............有问题？下一个循环的时候就没有了？无语。。
                    hashset.clear();
                    tepArr.clear();
                }
            }                     
            
		}catch(Exception e) {    
            e.printStackTrace();     
        }finally{
        	readwb.close();
        }
		
	}

}