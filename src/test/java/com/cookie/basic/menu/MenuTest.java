package com.cookie.basic.menu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class MenuTest {
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private MenuFilesMapper menuFilesMapper;

	
	//@Test
	public void mInserttest() throws Exception{
		MenuVO menuVO = new MenuVO();
		menuVO.setMmName("핫 라떼");
		menuVO.setMmPrice(2500);
		menuVO.setMmCount(200);
		menuVO.setCmNum(100);
		menuVO.setMmTemp(1);
		int result = menuMapper.menuInsert(menuVO);
	}
	
	//@Test
	public void test()throws Exception{
		List<MenuFilesVO> menuFilesVOs = new ArrayList<>();
		for(int i=0; i<2;i++) {
		MenuFilesVO menuFilesVO = new MenuFilesVO();
		menuFilesVO.setMfName(i+"번f");
		menuFilesVO.setMoName(i+"번O");
		menuFilesVO.setMmNum(i+3);
		menuFilesVOs.add(menuFilesVO);}
		menuFilesMapper.menuFilesInsert(menuFilesVOs);
		
		
	}
	
	//@Test
	public void mSelect()throws Exception{
		MenuVO menuVO = new MenuVO();
		menuVO.setMmNum(1);
	
		menuVO = menuMapper.menuSelect(menuVO);
		assertNotNull(menuVO.getMmName());
	}

	
	
	
	
}
