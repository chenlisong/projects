package com.company.java.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.java.algorithm.AhoCorasickDoubleArrayTrie;

public class Data {
	
	public AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
	
	//build时创建用来保存Map<keyword,id>数据
	public Map<String,Long> keyValues = new HashMap<String, Long>();
	
	public Map<Long,String> allMap = new HashMap<Long, String>();
	
	//拆分后的对应关系 key：违禁词（包含.*拆分后的词）  value：对应的id列表
	public Map<String,List<Long>> metadatas = new HashMap<String, List<Long>>();
}
