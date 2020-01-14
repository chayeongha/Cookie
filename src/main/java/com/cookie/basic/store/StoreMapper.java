package com.cookie.basic.store;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
@Mapper
public interface StoreMapper {

	public int storeInsert(StoreVO storeVO) throws Exception;

	public int storeUpdate(StoreVO storeVO) throws Exception;
	
	public StoreVO sNumSearch(StoreVO storeVO)throws Exception;

}
