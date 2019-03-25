package com.duolanjian.design.composite;

import java.util.List;

public class Client {

	public static void main(String[] args) {
		Branch ceo = compositeTree();
		
		System.out.println(ceo.getInfo());
		
		System.out.println(getTreeInfo(ceo));
		
	}
	
	public static Branch compositeTree() {
		Branch ceo = new Branch("张三", "CEO", 100000.0);
		
		Branch leader1 = new Branch("部门经理A", "部门经理", 61000.0);
		
		Branch leader2 = new Branch("部门经理B", "部门经理", 62000.0);
		
		Branch leader3 = new Branch("部门经理C", "部门经理", 63000.0);
		
		Leaf leaf1 = new Leaf("普通开发A", "普通开发", 11000.0);
		
		Leaf leaf2 = new Leaf("普通开发B", "普通开发", 12000.0);
		
		Leaf leaf3 = new Leaf("普通开发C", "普通开发", 13000.0);
		
		ceo.addSubOrdinate(leader1);
		ceo.addSubOrdinate(leader2);
		ceo.addSubOrdinate(leader3);

		leader1.addSubOrdinate(leaf1);
		leader1.addSubOrdinate(leaf2);
		leader2.addSubOrdinate(leaf3);
		
		return ceo;
	}
	
	public static String getTreeInfo(Branch branch) {
		List<Crop> subordinateList = branch.getSubOrdinate();
		
		String info = "";
		
		for(Crop crop : subordinateList) {
			if(crop instanceof Leaf) {
				info += crop.getInfo() + "\n";
			}else {
				info += crop.getInfo() + "\n" + getTreeInfo((Branch)crop);
			}
		}

		return info;
	}
}
