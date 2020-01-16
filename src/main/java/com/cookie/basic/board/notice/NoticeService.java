package com.cookie.basic.board.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookie.basic.util.Pager;

@Service
public class NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	
	//글 하나 조회
	public NoticeVO noticeSelect(NoticeVO noticeVO) throws Exception {
		return noticeMapper.noticeSelect(noticeVO);
	}
	
	//리스트
	public List<NoticeVO> noticeList(Pager pager) throws Exception {
		pager.makeRow();
		pager.makePage(noticeMapper.noticeCount(pager));
		
		return noticeMapper.noticeList(pager);
	}
}