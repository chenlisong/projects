package com.yinuo.service;
import com.yinuo.bean.Constant;
import com.yinuo.bean.Inititor;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.InititorMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.Config;
import com.yinuo.view.InititorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Transactional(rollbackFor = Exception.class)
@Service
public class InitItorService {
	
	@Autowired
	private InititorMapper mapper;

	@Autowired
	private CardService cardService;

	@Autowired
	private Config config;

	@Autowired
	private WechatService wechatService;

	//发起卡包
	public Inititor call(int userId, String formId) {
		int card = cardService.algorCard();

		Inititor data = mapper.selectCallUndo(userId);
		if(data != null) {
			return data;
		}

		Inititor inititor = new Inititor(userId, card, Constant.InititorState.NeedTodo, formId);
		CommonUtil.setDefaultValue(inititor);
		mapper.insert(inititor);
		return inititor;
	}

	//协助卡包
	public void help(int id, int userId, String formId) {

		//判断这个用户今天是否协助过
		int dailtTime = mapper.countTodayHelp(userId);
		if(dailtTime >= config.helpDailyTime) {
			throw new InvalidArgumentException(String.format("您今天已经帮助过%d次，明天重置该次数", dailtTime));
		}

		Inititor inititor = mapper.selectOne(id);

		if(userId == inititor.getUserId().intValue()) {
			throw new InvalidArgumentException("不能拆自己的卡包");
		}

		if(inititor.getState().intValue() == Constant.InititorState.Fix) {
			throw new InvalidArgumentException("该卡包已经被拆开过");
		}

		int card = cardService.algorCard();
		Inititor helper = new Inititor(userId, inititor.getId(), card, Constant.InititorState.Fix, formId);
		CommonUtil.setDefaultValue(helper);
		mapper.insert(helper);

		List<Inititor> data = mapper.selectByParentId(inititor.getId());
		data.add(inititor);

		//发放卡包
		if(data.size() >= config.helpCount) {

			cardService.insert(inititor.getUserId(), inititor.getCard());
			for(Inititor unit: data) {
				cardService.insert(unit.getUserId(), unit.getCard());
			}

			//更改inititor状态
			if(data != null && data.size() >= config.helpCount) {
				mapper.fix(inititor.getId(), Constant.InititorState.Fix);
			}

			//更新其未读状态
			List<Integer> ids = CommonUtil.entity(data, "id", Integer.class);
			ids.add(inititor.getId());
			mapper.updateReadState(ids, Constant.InititorReadState.Undo);

			//发送模板消息
			wechatService.inititorWechatMsg(data);
		}
	}

	public int update(Inititor inititor) {
		return mapper.update(inititor);
	}

	public Inititor selectById(int id) {
		return mapper.selectOne(id);
	}

	public List<Inititor> selectByUserId(int userId) {
		List<Inititor> list = mapper.selectByUserId(userId);

		if(list != null && list.size() > 0) {
		    List<Integer> ids = new ArrayList<Integer>();
		    for(Inititor unit: list) {
		        if(unit.getReadState().intValue() == Constant.InititorReadState.Undo) {
		            ids.add(unit.getId());
                }
            }

            if(ids.size() > 0) {
                mapper.updateReadState(ids, Constant.InititorReadState.Done);
            }
		}

		return list;
	}

	public List<Inititor> selectByParentId(int parentId) {
		return mapper.selectByParentId(parentId);
	}

	public List<InititorView> convert(List<Inititor> inititors) {
		List<InititorView> views = new ArrayList<InititorView>();

		if(inititors != null && !inititors.isEmpty()) {

			List<Integer> parentIds = CommonUtil.entitySkip(inititors, "parentId", Integer.class, 0);
			Map<Integer, Inititor> parentMap = new HashMap<Integer, Inititor>();
			if(parentIds != null && parentIds.size() > 0) {
				List<Inititor> parents = mapper.selectListByIds(parentIds);
				parentMap = CommonUtil.entityMap(parents, "id", Integer.class);
			}

			for(Inititor inititor: inititors) {
				views.add(new InititorView(inititor, parentMap.get(inititor.getParentId())));
			}
		}

		return views;
	}
}
