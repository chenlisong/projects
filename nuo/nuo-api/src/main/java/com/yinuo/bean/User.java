package com.yinuo.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.exception.InvalidHttpArgumentException;
import com.yinuo.validation.IsInt;
import com.yinuo.validation.IsString;
import org.apache.xmlbeans.impl.xb.ltgfmt.FileDesc;

import javax.persistence.RollbackException;


public class User {

	private Long id;
	
	@IsString(minLength=1,maxLength=31)
	private String wechatNickname;
	
	@IsString(minLength=1,maxLength=31)
	private String wechatOpenid;

	@IsInt(min=1,max=2)
	private int sex;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date birthday;
	
	@IsString(minLength=1,maxLength=255)
	private String avatarUrl;
	
	@IsString(minLength=1,maxLength=15)
	private String city;
	
	@IsString(minLength=1,maxLength=15)
	private String province;
	
	@IsString(minLength=1,maxLength=15)
	private String country;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private Manager manager;

	private List<ManagerClass> managerClassList;

	private Integer role;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWechatNickname() {
		return wechatNickname;
	}

	public void setWechatNickname(String wechatNickname) {
		this.wechatNickname = wechatNickname;
	}

	public String getWechatOpenid() {
		return wechatOpenid;
	}

	public void setWechatOpenid(String wechatOpenid) {
		this.wechatOpenid = wechatOpenid;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.birthday = sdf.parse(birthday);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
		}
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.createTime = sdf.parse(createTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
		}
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<ManagerClass> getManagerClassList() {
		return managerClassList;
	}

	public void setManagerClassList(List<ManagerClass> managerClassList) {
		this.managerClassList = managerClassList;
		if(managerClassList != null && managerClassList.size() > 0) {
			for(ManagerClass managerClass: managerClassList) {
				if(role == null || managerClass.getRole().intValue() < role.intValue()) {
					setRole(managerClass.getRole());
				}
			}
		}
	}

	public void checkLevel(int needRole, long schoolId, long classId) {
		if(manager == null || managerClassList == null || managerClassList.size() <= 0) {
			throw new InvalidArgumentException("当前操作超出权限");
		}

		boolean flag = false;
		managerLoop: for(ManagerClass managerClass: managerClassList) {
			if(managerClass.getRole() == Constant.Role.Manager) {
				flag = true;
				break managerLoop;
			}else if(needRole == Constant.Role.School && managerClass.getRole() == Constant.Role.School
					&& managerClass.getSchoolId().longValue() == schoolId) {
				flag = true;
				break managerLoop;
			}else if(needRole == Constant.Role.Teacher) {
				if(managerClass.getRole() == Constant.Role.School && managerClass.getSchoolId().longValue() == schoolId) {
					flag = true;
					break managerLoop;
				}else if(managerClass.getRole() == Constant.Role.Teacher && managerClass.getClassId().longValue() == classId) {
					flag = true;
					break managerLoop;
				}
			}
		}
		if(!flag) {
			throw new InvalidArgumentException("当前操作超出权限");
		}
	}

	public void checkLevel(int needRole) {
		if(manager == null || managerClassList == null || managerClassList.size() <= 0) {
			throw new InvalidArgumentException("当前操作超出权限");
		}

		boolean flag = false;
		managerLoop: for(ManagerClass managerClass: managerClassList) {
			if(managerClass.getRole() <= needRole) {
				flag = true;
				break managerLoop;
			}
		}
		if(!flag) {
			throw new InvalidArgumentException("当前操作超出权限");
		}
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String toString() {
		try {
			return JSON.toJSONString(this);
		} catch (Exception e) {
		}
		return "json parse failed";
	}
}
