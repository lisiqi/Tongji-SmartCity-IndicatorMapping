package priv.siqi.java;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;   
import java.io.FileInputStream;   
  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;   
  
    
  





import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;
import priv.siqi.java.CreateDictionary;








import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import jxl.Cell;   
//  
//import jxl.CellType;   
//  
//import jxl.Sheet;   
//  
//import jxl.Workbook;   
//  
//import jxl.write.Label; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class App {


	
/*测试操作Excel*/	
//	public static void main(String[] args)    
//	  
//    {   
//  
//        jxl.Workbook readwb = null;   
//  
//      //写user_dict.txt
//    	BufferedWriter fw = null;
//    	File wfile = new File("user_dict.txt");
//		try {
//			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfile, true), "UTF-8"));// 指定编码格式，以免读取时中文字符异常
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} 
//		
//		ArrayList wordList = new ArrayList();
//		
//		
//        try    
//  
//        {   
//        	
////        	try {
////        		
////        		fw.append("我写入的内容2");
////        		fw.newLine();
////        		fw.append("我又写入的内容");
////        		fw.flush(); // 全部写入缓存中的内容
////        	} catch (Exception e) {
////        		e.printStackTrace();
////        	} finally {
////        		if (fw != null) {
////        			try {
////        				fw.close();
////        			} catch (IOException e) {
////        				e.printStackTrace();
////        			}
////        		}
////        	}
//        	
//            
//        	
//        	//构建Workbook对象, 只读Workbook对象   
//  
//            //直接从本地文件创建Workbook   
//  
//            InputStream instream = new FileInputStream("src/main/resources/最新数据库指标.xls");   
//
//            readwb = Workbook.getWorkbook(instream);   
//  
//            File dfile = new File("src/main/resources/dict.txt");
//            FileReader fr = new FileReader(dfile);
//            BufferedReader br = new BufferedReader(fr);
//            br.mark((int)dfile.length()+1);
//    
//  
//            //Sheet的下标是从0开始   
//  
//            //获取第一张Sheet表     
//            Sheet readsheet = readwb.getSheet(0);   
//  
//            //获取Sheet表中所包含的总列数     
//            int rsColumns = readsheet.getColumns();   
//  
//            //获取Sheet表中所包含的总行数    
//            int rsRows = readsheet.getRows();             
//  
//            //获取指定单元格的对象引用     
//            for (int i = 0; i < rsRows; i++)   
//  
//            {   
//  
//                for (int j = 0; j < rsColumns; j++)   
//  
//                {   
//  
//                    Cell cell = readsheet.getCell(j, i); 
//                    String indicator = new String(cell.getContents());
//  
// //                   System.out.print(cell.getContents() + " ");
//                    JiebaSegmenter segmenter = new JiebaSegmenter();
//     //               System.out.println(segmenter.sentenceProcess(indicator).toString());
//                    
//                    
//                    List<String> strings = segmenter.sentenceProcess(indicator);
//                    for(String s : strings){
//                    	
//                        String nextLine;
//                        
//                        
// //                       System.out.println(s);
//                        
//                    	int line = 1;
//                    	while((nextLine=br.readLine())!=null){
//                    		//只要nextLine的第一部分，即空格以前
//                    		String[] lArray = nextLine.split(" "); 
//                    		
//	                        if(lArray[0].equals(s)){
////	                            System.out.println(line+"行  "+ nextLine);
//	                        	wordList.add(nextLine);
////	                            fw.append(nextLine); 
////	                            //此处不应该是简单的append，应该是去重插入。两种方案：1.先全部append完了之后再去重排序；——适合初次建立词库的时候。后续更新词库再进行sort是否会效率低？2.插入的时候就按序插入并去重。
////	                            fw.newLine();
//	                        }
//	                        line++;
//	                    }
//                    	
//
//                    	br.reset();
//                    }                    
//                }     
//            }  
//            
//            br.close();
////以下部分效率很好            
//            removeDuplicate(wordList);
//            Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
//            String[] arr = (String[])wordList.toArray(new String[wordList.size()]);
//            Arrays.sort(arr, cmp);
//            for(int i=0;i<arr.length;i++){
//                   //System. out.println(arr[i]);//正确 接着写到文件里
//            	fw.append(arr[i]);
//            	fw.newLine();
//            }
//            
//  
//            /*   
//  
//            //利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄   
//  
//            jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(   
//  
//                    "F:/红楼人物1.xls"), readwb);   
//  
//            //读取第一张工作表   
//  
//            jxl.write.WritableSheet ws = wwb.getSheet(0);   
//  
//            //获得第一个单元格对象   
//  
//            jxl.write.WritableCell wc = ws.getWritableCell(0, 0);   
//  
//            //判断单元格的类型, 做出相应的转化   
//  
//            if (wc.getType() == CellType.LABEL)    
//  
//            {   
//  
//                Label l = (Label) wc;   
//  
//                l.setString("新姓名");   
//  
//            }   
//  
//            //写入Excel对象   
//  
//            wwb.write();   
//  
//            wwb.close();   
//            
//            */
//  
//        } catch (Exception e) {   
//  
//            e.printStackTrace();   
//  
//        } finally {   
//  
//            readwb.close();   
//            if (fw != null) {
//    			try {
//    				fw.close();
//    			} catch (IOException e) {
//    				e.printStackTrace();
//    			}
//    		}
//  
//        }   
//  
//    }   
//	
///*测试txt搜索*/	 //-通过
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		//搜索dict.txt
//    	String fileName = "src/main/resources/dict.txt";
//        FileReader fr;
//		try {
//			fr = new FileReader(fileName);
//			BufferedReader br = new BufferedReader(fr);
//			String nextLine;
//			int line=1;
//            while((nextLine=br.readLine())!=null){
//              if(nextLine.indexOf("生")>-1){
//                  System.out.println(line+"行  "+nextLine);
//              }
//              line++;
//          }
//		
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
/*测试写txt*/	 //通过
//	public static void main(String[] args) {
//	BufferedWriter fw = null;
//	try {
//		File file = new File("text.txt");
//		fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
//		fw.append("我写入的内容2");
//		fw.newLine();
//		fw.append("我又写入的内容");
//		fw.flush(); // 全部写入缓存中的内容
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		if (fw != null) {
//			try {
//				fw.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	}
	public static void main(String[] args) {
//		CreateDictionary newDict = new CreateDictionary("src/main/resources/构建词库用.xls");
//测试写Excel
//
		MapIndicator mi = new MapIndicator();
		mi.mapping();
		
//		try{
//			jxl.Workbook readwb1 = null; 
//			InputStream instream1 = new FileInputStream("src/main/resources/最新数据库指标.xls");   
//			readwb1 = Workbook.getWorkbook(instream1);
//			//利用已经创建的Excel工作薄,创建新的可写入的Excel工作薄   	  
//			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File("映射结果.xls"), readwb1);
//			
//			WriteExcel we = new WriteExcel();
//			we.writeCell(wwb, 2, "hahahahahahahahahahahahah");
//		
//			readwb1.close();
//			wwb.close();//close的时候才写成！
//		}catch(Exception e) {    
//            e.printStackTrace();
//        }
		 
//		try {
//			InputStream inp = new FileInputStream("src/main/resources/最新数据库指标.xls");
//			Workbook wb1 = WorkbookFactory.create(inp);
//		    
//		    Workbook wb = new HSSFWorkbook();
//			wb = wb1;
//			
//
//		    
//			FileOutputStream fileOut = new FileOutputStream("映射结果.xls");		    
//		    wb.write(fileOut);
//		    fileOut.close();
//		    
//		
//		    
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
	}
}
