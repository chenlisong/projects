package com.yinuo.service;
import com.yinuo.bean.Constant;
import com.yinuo.bean.Transfer;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.TransferMapper;
import com.yinuo.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class TransferService {
	
	@Autowired
	private TransferMapper mapper;

	@Autowired
	private CardService cardService;

	@Autowired
	private WechatService wechatService;

	//赠送，索要
	public int transfer(int userId, int type, int card, String formId) {

		//如果是赠送类型，判断是否拥有该卡片
		if(type == Constant.TransferType.Send) {
			int cardCount = cardService.selectByUserCardId(userId, card);
			if(cardCount <= 0) {
				throw new InvalidArgumentException("您不存在这张卡片，无法赠送");
			}
		}

		//如果存在记录，直接返回id
		List<Transfer> data = mapper.selectBySrcId(userId, type, card, Constant.TransferState.NeedTodo);
		if(data != null && data.size() > 0) {
			return data.get(0).getId();
		}

		Transfer transfer = new Transfer(userId, type, card, Constant.TransferState.NeedTodo, formId);
		CommonUtil.setDefaultValue(transfer);
		mapper.insert(transfer);

		return transfer.getId();
	}

	public void fix(int id, int userId, String formId) {

		Transfer transfer = mapper.selectOne(id);

		if(transfer.getSrcId().intValue() == userId) {
			throw new InvalidArgumentException("您无法操作自己发起的赠送、索要");
		}

		if(transfer.getState().intValue() != Constant.TransferState.NeedTodo) {
			throw new InvalidArgumentException("该次赠送/索要请求已经被处理");
		}

		if(transfer.getType().intValue() == Constant.TransferType.Send) {

			//删除赠送者的card
			int count = cardService.updateNumber(transfer.getSrcId(), transfer.getCard(), -1);
			if(count <= 0) {
				throw new InvalidArgumentException("赠送者卡片不足，领取失败");
			}

			//增加target的card
			cardService.insert(userId, transfer.getCard());
		}else if(transfer.getType().intValue() == Constant.TransferType.Get) {

			int count = cardService.updateNumber(userId, transfer.getCard(), -1);
			if(count <= 0) {
				throw new InvalidArgumentException("赠送者卡片不足，赠送失败");
			}

			cardService.insert(transfer.getSrcId(), transfer.getCard());
		}

		//更新transfer状态
		mapper.fix(transfer.getId(), userId, Constant.TransferState.Agree, formId);

		//发送模板消息
        transfer.setTargetId(userId);
        transfer.setFormIdTwo(formId);
        transfer.setState(Constant.TransferState.Agree);
		wechatService.transferWechatMsg(transfer);

	}

	public void refuse(int id, int userId, String formId) {

		Transfer transfer = mapper.selectOne(id);

		if(transfer.getSrcId().intValue() == userId) {
			throw new InvalidArgumentException("您无法拒绝自己发起的赠送、索要");
		}

		//更新transfer状态
		mapper.fix(transfer.getId(), userId, Constant.TransferState.Refuse, formId);
	}

	public List<Transfer> selectByUserId(int userId, int type) {
		return mapper.selectBySrcId(userId, type, 0, 0);
	}

}
