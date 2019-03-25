package com.company.java.algorithm;

import java.util.Comparator;

import com.company.java.algorithm.AhoCorasickDoubleArrayTrie.Hit;

/**
 * 提供Hit排序
 * @author youku
 *
 */
@SuppressWarnings("rawtypes")
public class HitComparator implements Comparator<Hit>{

	@Override
	public int compare(Hit o1, Hit o2) {
		return o1.begin - o2.begin == 0 ? o1.end - o2.end : o1.begin - o2.begin;
	}

}
