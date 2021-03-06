package priv.siqi.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.write.WritableWorkbook;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class MapIndicator {
	
	public void mapping(){
		int countTep;//记录相同的个数
		int count;
		HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();//记录数据库指标序号,可能有多个
		
		ArrayList<String> arrIndi = new ArrayList<String>();
		
		HashSet<String> hashset = new HashSet<String>();
//		jxl.Workbook readwb = null;
		WriteExcel we = new WriteExcel();
		
		SegmentDBIndicator segDBIndi = new SegmentDBIndicator();
		
		try{
			
			
//			jxl.Workbook readwb1 = null; 
//			InputStream instream1 = new FileInputStream("src/main/resources/最新数据库指标.xls");   
//			readwb1 = Workbook.getWorkbook(instream1);
//			//利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄   	  
//			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File("映射结果.xls"), readwb1);
//			
			
			//打开 待整理指标
			InputStream instream = new FileInputStream("src/main/resources/待整理指标.xls");
			Workbook wb = WorkbookFactory.create(instream);
			
			Sheet sheet = wb.getSheetAt(0);
			
			for (Row row:sheet)   
	            {    
	                for (Cell cell : row)   //每一个待映射指标
	                {   
	                    String indicator = new String(cell.getStringCellValue());  //取到待映射指标
	                    
	                    JiebaSegmenter segmenter = new JiebaSegmenter(); 
	                    
	                    //细粒度分词
	                    List<SegToken> segTokenList = segmenter.process(indicator, SegMode.INDEX);
	                    for(SegToken segToken : segTokenList){
	                    	String word = segToken.word;
	                    	if(word.matches(" *") == false){ //用regular expression 把长串空格去掉
	                    		hashset.add(word);
	                    	}	
	        			}
//	                    List<String> strings = segmenter.sentenceProcess(indicator);
//	                    for(String s : strings){
//	                    	hashset.add(s); //把分词后的结果存到HashSet里,没有重复值！
//	                    }
	                    
	                    
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
	                    		if(count !=0){//(0,0)?
	                    			map.put(k,count);  
	                    		}
	                    	}
	                    	countTep = 0;                   	
	                    }
	                    
	                    arrIndi.clear();
	                    hashset.clear();
	                    
	                    if(map.isEmpty() == false){
	                    	//拿到中count的最大值并写Excel
		                    int maxValueInMap = Collections.max(map.values());                    
		                    for(Entry<Integer, Integer> entry : map.entrySet()){
		                    	if(entry.getValue() == maxValueInMap){
		                    		
		                    		int kRow = entry.getKey();//拿到了哪几个数据库指标
		                    		
		                    		//往这几个数据库指标中映射
		                    		//写Excel
		                    		we.writeCell(kRow, indicator);
//		                    		Workbook workbook = Workbook.getWorkbook(new File("映射结果.xls"));
//		                    		WritableWorkbook copy = Workbook.createWorkbook(new File("映射结果.xls"), workbook);
//		                    		we.writeCell(copy, kRow, indicator);
//		                    		copy.close();
		                    		
		                    	}
		                    }
	                    }
	                    map.clear();
	                    
	                }
	            }

			
//			InputStream instream = new FileInputStream("src/main/resources/待整理指标.xls"); //需要另存为xls而不能直接修改后缀名  

//            readwb = Workbook.getWorkbook(instream);   
          //Sheet的下标是从0开始   
            
            //获取第一张Sheet表     
//            Sheet readsheet = readwb.getSheet(0);   
  
            //获取Sheet表中所包含的总列数     
//            int rsColumns = readsheet.getColumns();   
//  
//			int rsColumns = sheet.
//			
//            //获取Sheet表中所包含的总行数    
//            int rsRows = readsheet.getRows();             
  
            //获取指定单元格的对象引用     
//            for (int i = 0; i < rsRows; i++)   
//            {    
//                for (int j = 0; j < rsColumns; j++)   //每一个待映射指标
//                {   
//                	Cell cell = readsheet.getCell(j, i); 
//                    String indicator = new String(cell.getContents());  //取到待映射指标
//                    
//                    JiebaSegmenter segmenter = new JiebaSegmenter(); 
//                    
//                    //细粒度分词
//                    List<SegToken> segTokenList = segmenter.process(indicator, SegMode.INDEX);
//                    for(SegToken segToken : segTokenList){
//                    	String word = segToken.word;
//                    	if(word.matches(" *") == false){ //用regular expression 把长串空格去掉
//                    		hashset.add(word);
//                    	}	
//        			}
////                    List<String> strings = segmenter.sentenceProcess(indicator);
////                    for(String s : strings){
////                    	hashset.add(s); //把分词后的结果存到HashSet里,没有重复值！
////                    }
//                    
//                    
//                    arrIndi.addAll(hashset);//arrIndi存了单个indicator的分词结果
//                    
//                    //进行比较
//                    countTep = 0;
//                    count = 0;
//                
//                    for(int k = 0; k<segDBIndi.getDbArr().size(); k++){ //每一个数据库指标  //没有东西
//                    	for(int x = 0; x<arrIndi.size(); x++){
//                    		for(int y = 0; y<segDBIndi.getDbArr().get(k).size(); y++){
//                    			if(arrIndi.get(x).equals(segDBIndi.getDbArr().get(k).get(y))){
//                    				countTep++;                    				
//                    			}
//                    		}
//                    	}
//                    	if(countTep >= count) 
//                    	{
//                    		count = countTep;
//                    		if(count !=0){//(0,0)?
//                    			map.put(k,count);  
//                    		}
//                    	}
//                    	countTep = 0;                   	
//                    }
//                    
//                    arrIndi.clear();
//                    hashset.clear();
//                    
//                    if(map.isEmpty() == false){
//                    	//拿到中count的最大值并写Excel
//	                    int maxValueInMap = Collections.max(map.values());                    
//	                    for(Entry<Integer, Integer> entry : map.entrySet()){
//	                    	if(entry.getValue() == maxValueInMap){
//	                    		
//	                    		int kRow = entry.getKey();//拿到了哪几个数据库指标
//	                    		
//	                    		//往这几个数据库指标中映射
//	                    		//写Excel
////	                    		Workbook workbook = Workbook.getWorkbook(new File("映射结果.xls"));
////	                    		WritableWorkbook copy = Workbook.createWorkbook(new File("映射结果.xls"), workbook);
////	                    		we.writeCell(copy, kRow, indicator);
////	                    		copy.close();
//	                    		
//	                    	}
//	                    }
//                    }
//                    map.clear();
//                    
//                }
//            }
//            
////            wwb.close();  //全部处理完才能写进去。。。。
//            readwb1.close();
		}catch(Exception e) {    
            e.printStackTrace();     
        }finally{
//        	readwb.close();
        	
        }
	}
	
	
}
