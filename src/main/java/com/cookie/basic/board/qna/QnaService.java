package com.cookie.basic.board.qna;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cookie.basic.util.Pager;

@Service
public class QnaService {

	@Autowired
	private QnaMapper qnaMapper;
	
	
	//글 삭제
	@Transactional
	public int qnaDelete(QnaVO qnaVO) throws Exception {
		
		return qnaMapper.qnaDelete(qnaVO);
	}
	
	//글 수정
	public int qnaUpdate(QnaVO qnaVO) throws Exception {
		
		return qnaMapper.qnaUpdate(qnaVO);
	}
	
	//글 작성
	@Transactional
	public int qnaWrite(QnaVO qnaVO) throws Exception {
		int result = qnaMapper.qnaWrite(qnaVO);
		result = qnaMapper.qnaRef(qnaVO);
		
		return result;
	}
	
	//글 하나 조회
	public QnaVO qnaSelect(QnaVO qnaVO) throws Exception {
		
		return qnaMapper.qnaSelect(qnaVO);
	}
	
	//리스트
	public List<QnaVO> qnaList(Pager pager) throws Exception {
		pager.makeRow();
		pager.makePage(qnaMapper.qnaCount(pager));
		
		return qnaMapper.qnaList(pager);
	}
}
