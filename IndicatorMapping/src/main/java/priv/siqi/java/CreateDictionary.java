package priv.siqi.java;

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
import java.util.Iterator;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class CreateDictionary {
	private String filename;
	private ArrayList<String> dictArr;
	private ArrayList<String> newDictArr; 
	
	
	public CreateDictionary(String filename){
		this.filename = filename;
		dictArr = new ArrayList<String>();
		newDictArr = new ArrayList<String>();
		writeNewDict();
	}
	
	private ArrayList<String> divideIndicatorToArr(){ 
		ArrayList<String> divideResultArr = new ArrayList<String>();
		HashSet<String> hashset = new HashSet<String>();
				
		jxl.Workbook readwb = null;
	
		try{
			InputStream instream = new FileInputStream(filename);   

            readwb = Workbook.getWorkbook(instream);   
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
                for (int j = 0; j < rsColumns; j++)   
                {   
                	Cell cell = readsheet.getCell(j, i); 
                    String indicator = new String(cell.getContents());
                    
                    JiebaSegmenter segmenter = new JiebaSegmenter();                            
                    List<String> strings = segmenter.sentenceProcess(indicator);
                    for(String s : strings){
                    	hashset.add(s); //把分词后的结果存到HashSet里,没有重复值！
                    }
                }
            }
                      
            divideResultArr.addAll(hashset);
            
		}catch(Exception e) {    
            e.printStackTrace();     
        }finally{
        	readwb.close();
        }
		
		return divideResultArr;
	}
	
	private void readDictToArr(){	
		File dfile = new File("src/main/resources/dict.txt");
        FileReader fr;
        
		try {
			fr = new FileReader(dfile);
			BufferedReader br = new BufferedReader(fr);
			
			String nextLine;
			while((nextLine=br.readLine())!=null){
				dictArr.add(nextLine);
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
	}
	
	private void findWord(){		
		readDictToArr();
		ArrayList<String> divideResultArr = divideIndicatorToArr();
				
		for(int i=0; i<divideResultArr.size(); i++){
			for(int j=0; j<dictArr.size(); j++){
				//只要dictArr.get(j)的第一部分，即空格以前
        		String[] lArr = dictArr.get(j).split(" ");
        		if(lArr[0].equals(divideResultArr.get(i))){ //找到了
        			newDictArr.add(dictArr.get(j));
        		}
			}
		}
		
	}
	
	private void writeNewDict(){
		findWord();
		//写user_dict.txt
    	BufferedWriter fw = null;
    	File wfile = new File("user_dict.txt");
		try {
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfile, true), "UTF-8"));// 指定编码格式，以免读取时中文字符异常
			
			Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
            String[] arr = (String[])newDictArr.toArray(new String[newDictArr.size()]);
            Arrays.sort(arr, cmp);
            for(int i=0;i<arr.length;i++){
                   //System. out.println(arr[i]);//正确 接着写到文件里
            	fw.append(arr[i]);
            	fw.newLine();
            }
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (fw != null) {
    			try {
    				fw.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
		}
	}

}
