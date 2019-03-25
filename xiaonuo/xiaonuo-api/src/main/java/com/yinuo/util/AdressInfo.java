package com.yinuo.util;

public enum AdressInfo {
	ZYGXY_1("中原工学院西区1#寝室楼", "34.7457604217,113.5901382565", 1), ZYGXY_2("中原工学院西区2#寝室楼", "34.7457582178,113.5892692208", 2), ZYGXY_3("中原工学院西区3#寝室楼", "34.7465648566,113.5900792480", 3);  
    // 成员变量  
    private String name;  
    private String xy;
    private int index;  
    // 构造方法  
    private AdressInfo(String name, String xy, int index) {  
        this.name = name;  
        this.xy = xy;
        this.index = index;  
    }  
    // 普通方法  
    public static String getName(int index) {  
        for (AdressInfo c : AdressInfo.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    }
    public static String getXy(int index) {  
        for (AdressInfo c : AdressInfo.values()) {  
            if (c.getIndex() == index) {  
                return c.xy;  
            }  
        }  
        return null;  
    }
    public static int distance(String xy) {
    	double[] d1=xyArray(xy);
    	double min=Double.MAX_VALUE;
    	int index=0;
    	for (AdressInfo c : AdressInfo.values()) {  
             	double[] d2=xyArray(c.getXy());
             	index=min<compare(d1,d2)? index:c.getIndex();
        }
    	 return index;
	}

    public static double compare(double[] d1,double[] d2) {
		return (d1[1]-d2[1])*(d1[1]-d2[1])+(d1[2]-d2[2])*(d1[2]-d2[2]);
    	
	}
    private static double[] xyArray(String xy) {
		double[] ds=new double[2];
    	ds[1] = new Double(xy.substring(0,xy.indexOf(",")));
    	ds[2] = new Double(xy.substring(xy.indexOf(",")));
    	System.out.println(ds[1] +""+ds[2]);
		return ds;
	}
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public int getIndex() {  
        return index;  
    }  
	public String getXy() {
		return xy;
	}

}
