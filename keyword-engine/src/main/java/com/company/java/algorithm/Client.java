package com.company.java.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.company.java.bean.Data;
import com.company.java.exception.BuildRepeatException;
import com.company.java.exception.FileNotExistException;
import com.company.java.exception.ResourceNotFoundException;
import com.company.java.util.Config;
/**
 *
 * @author chenlisong@youku.com
 * AC自动机算法，并且支持  XXX.*XXX模糊查找
 * 思路：build时增加一个Map<String,Long[]> Map<keyword,id[]> 用来拆分保存模糊的数据  
 *
 */
@Component
public class Client {
	
	@Autowired
	private Config config;

	private static Logger log = LoggerFactory.getLogger(Client.class);
	
	private TreeMap<String,String> keys = new TreeMap<String, String>();
	
	private volatile Data data = new Data();
	
	private Data dataBak = new Data();
	
	//仅用来避免生成多个对象
	private List<Long> tempmetadataids = new ArrayList<Long>();
	
	//需要拆分的词
	private static String matchStr = ".*";
	
	private static String splitStr = "\\.\\*";
	
	private static boolean buildLock = true;
	
	private static final HitComparator hitComparator = new HitComparator();
	
	private Client(){};
	
	/* stat 所需要数据*/
	//总匹配数
	public static int matchTotal = 0;
	//总创建数
	public static int buildTotal = 0;
	//创建时间戳
	public static Date buildTime = new Date();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@PostConstruct
	public void instance(){
		try {
			build(config.defaultFile);
		} catch (Exception e) {
			log.error("load malformedword.properties sysname,scename error.");
		}
		
	}
	
	/**
	 * 构建双数组查询树
	 * @param path 违禁词字典文件路径
	 * @return	{build_total:"",cost:""} build_total字典行数，cost执行时间。
	 * @throws IOException 
	 */
	public Map<String,Object> build (String path){
		
		if(buildLock == false){
			throw new BuildRepeatException("BUILD_REPEAT");
		}
		
		if(!new File(path).exists()){
			throw new FileNotExistException("FILE_NOT_FOUND");
		}
		try {
			buildLock = false;
			int build_total = 0;
			long start = System.currentTimeMillis();
			//在这里处理metadatas
			dataBak = new Data();
			build_total = loadDictionary(path);
			//先在bak上build，然后再将引用切换过去。
			if(keys != null && keys.size() > 0){
				dataBak.acdat.build(keys);
				data = dataBak;
				
				dataBak = null;
			}else{
				log.error("malformedword is null . path："+path);
			}
				
			long time = System.currentTimeMillis() - start;
			
			Map<String,Object> json = new HashMap<String, Object>();
			json.put("build_total", build_total);
			log.debug("build success. cost:"+time+",file:"+path);
			return json;
		} catch (Exception e) {
			log.error("build error.", e);
			throw new IllegalArgumentException(e);
		}finally{
			buildLock = true;
		}
	}
	
	public Map<String,Object> query(String text){
		try {
			if((data.acdat==null) || (data.acdat.base == null && data.acdat.check == null && data.acdat.fail == null && data.acdat.l == null
					&& data.acdat.output == null && data.acdat.size == 0 && data.acdat.v == null)){
				throw new ResourceNotFoundException("RESOUCE_NOT_FOUND");
			}
			List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList = data.acdat.parseText(text);
			//对hit进行排序
	        Collections.sort(wordList, hitComparator);
	        //封装iddatas
	        Map<String,Object> json = new HashMap<String, Object>();
	        List<Object> matches = new ArrayList<Object>();
	        int total = 0 ;

	        Map<Long,List<String>> iddatas = new HashMap<Long, List<String>>();
			if(wordList != null && wordList.size() > 0){
				//对结果转换成iddatas存储
				for(AhoCorasickDoubleArrayTrie<String>.Hit<String> hit:wordList){
					addIdDatas(hit.value,iddatas);
				}
				
				StringBuffer sb ;
				
				List<String> keywords;
				for(Long id:iddatas.keySet()){
					keywords = iddatas.get(id);
					if(keywords.size() > 0){
						sb = new StringBuffer();
						for(int i=0;i<keywords.size();i++){
							if(i == keywords.size() - 1){
								sb.append(keywords.get(i));
							}else{
								sb.append(keywords.get(i)+matchStr);
							}
						}
						String instanceKeyword = data.allMap.get(id);
						if(sb.toString().contains(instanceKeyword)){
							Map<String,Object> temp = new HashMap<String, Object>();
							temp.put("id", id);
			        		temp.put("word", instanceKeyword);
							total ++ ;
							matches.add(temp);
						}
					}
				}
			}
			json.put("total", total);
			json.put("matches", matches);			
			return json;
		} catch (Exception e) {
			log.error("query error.", e);
			throw new IllegalArgumentException(e);
		}
	}
	
	public Map<String,Object> stat(){
		Map<String,Object> json = new HashMap<String, Object>();
		json.put("building", buildLock ? 0 : 1);
		json.put("match_total", matchTotal);
		json.put("build_total", buildTotal);
		json.put("build_time", sdf.format(buildTime));
		
		JSONObject automata = new JSONObject();
		automata.put("total_word", data.keyValues.size());
		automata.put("base_length", data.acdat.base.length);
		automata.put("check_length", data.acdat.check.length);
		json.put("automata", automata);
		return json;
	}
	
	//解析违禁词文本
	public int loadDictionary(String path) throws IOException
    {
		int count = 0;
		long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
        String line;
        keys = new TreeMap<String, String>();
        
        Pattern pattern = Pattern.compile("\\d{1,10}	");
        
        long maxsize = config.maxsize;
        
        while ((line = br.readLine()) != null)
        {
        	if(line.length() > maxsize){
        		log.info("the key :"+line+" is pass,because it's length is "+line.length()+" greater than maxsize "+maxsize);
        		continue;
        	}
            segmentString(pattern.matcher(line),line);
            count ++ ;
        }
        br.close();
        long time = System.currentTimeMillis() - start;
        log.info("load dictionary. path:"+path+",cost time:"+time);
        return count;
    }
	
	//分割字符串 例如：“196621	国王归来” 取结果“196621	国王归来”
	public void segmentString(Matcher matcher ,String text){
		String result = "";
		
		if(matcher.find()){
			long id = Long.parseLong(matcher.group(0).replace("	", ""));
			result = text.replaceAll(matcher.group(0), "");
			dataBak.keyValues.put(result, id);
			dataBak.allMap.put(id, result);
			
			if(result.indexOf(matchStr) > -1){
				for(String str:result.split(splitStr)){
					addMetaDatas(id, str);
					keys.put(str, str);
				}
			}else{
				keys.put(result, result);
			}
		}
	}
	
	public void addMetaDatas(long id,String keyword){
		tempmetadataids = dataBak.metadatas.get(keyword);
		if(tempmetadataids == null){
			tempmetadataids = new ArrayList<Long>();
		}
		if(!tempmetadataids.contains(id)){
			tempmetadataids.add(id);
		}
		dataBak.metadatas.put(keyword, tempmetadataids);
	}
	public void addIdDatas(String keyword,Map<Long,List<String>> iddatas){

		Long id = data.keyValues.get(keyword);
		List<Long> ids = data.metadatas.get(keyword);
		if(id!=null && id > 0){
			List<String> keywords = iddatas.get(id);
			if(keywords == null){
				keywords = new ArrayList<String>();
			}
			keywords.add(keyword);
			iddatas.put(id, keywords);
		}
		if(ids != null && ids.size() > 0){
			if(ids != null && ids.size() > 0){
				for(Long lid : ids){
					
					List<String> keywords = iddatas.get(lid);
					if(keywords == null){
						keywords = new ArrayList<String>();
					}
					keywords.add(keyword);
					iddatas.put(lid, keywords);
					
				}
			}
		}

	}

}
