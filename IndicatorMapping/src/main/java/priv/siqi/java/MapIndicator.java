package priv.siqi.java;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.huaban.analysis.jieba.JiebaSegmenter;

public class MapIndicator {
	
	public void mapping(){
		int countTep;//记录相同的个数
		int count;
		HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();//记录数据库指标序号,可能有多个
		
		ArrayList<String> arrIndi = new ArrayList<String>();
		
		HashSet<String> hashset = new HashSet<String>();
		jxl.Workbook readwb = null;
		WriteExcel we = new WriteExcel();
		
		SegmentDBIndicator segDBIndi = new SegmentDBIndicator();
		
		try{
			InputStream instream = new FileInputStream("src/main/resources/待整理指标.xls");   

            readwb = Workbook.getWorkbook(instream);   //.......出异常,需要另存为xls而不能直接修改后缀名
          //Sheet的下标是从0开始   
            
            //获取第一张Sheet表     
            Sheet readsheet = readwb.getSheet(0);   
  
            //获取Sheet表中所包含的总列数     
            int rsColumns = readsheet.getColumns();   
  
            //获取Sheet表中所包含的总行数    
            int rsRows = readsheet.getRows();             
  
            //获取指定单元格的对象引用     
            for (int i = 0; i < rsRows; i++)   
            {    
                for (int j = 0; j < rsColumns; j++)   //每一个待映射指标
                {   
                	Cell cell = readsheet.getCell(j, i); 
                    String indicator = new String(cell.getContents());
                    
                    JiebaSegmenter segmenter = new JiebaSegmenter();                            
                    List<String> strings = segmenter.sentenceProcess(indicator);
                    for(String s : strings){
                    	hashset.add(s); //把分词后的结果存到HashSet里,没有重复值！
                    }
                    arrIndi.addAll(hashset);//arrIndi存了单个indicator的分词结果
                    
                    //进行比较
                    countTep = 0;
                    count = 0;
                
                    for(int k = 0; k<segDBIndi.getDbArr().size(); k++){ //每一个数据库指标  //没有东西
                    	for(int x = 0; x<arrIndi.size(); x++){
                    		for(int y = 0; y<segDBIndi.getDbArr().get(k).size(); y++){
                    			if(arrIndi.get(x).equals(segDBIndi.getDbArr().get(k).get(y))){
                    				countTep++;                    				
                    			}
                    		}
                    	}
                    	if(countTep >= count) 
                    	{
                    		count = countTep;
                    		map.put(k,count);
                    	}
                    	countTep = 0;                   	
                    }
                    
                    if(map.isEmpty() == false){
                    	//拿到中count的最大值并写Excel
	                    int maxValueInMap = Collections.max(map.values());
	                    for(Entry<Integer, Integer> entry : map.entrySet()){
	                    	if(entry.getValue() == maxValueInMap){
	                    		
	                    		int kRow = entry.getKey();//拿到了哪几个数据库指标
	                    		
	                    		//往这几个数据库指标中映射
	                    		//写Excel
	                    		we.writeCell(kRow, indicator);
	                    		
	                    		
	                    	}
	                    }
                    }
                    map.clear();
                    
                }
            }
		}catch(Exception e) {    
            e.printStackTrace();     
        }finally{
        	readwb.close();
        }
	}
	
	
}