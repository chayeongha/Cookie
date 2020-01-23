package com.cookie.basic.store;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cookie.basic.util.FilePathGenerator;
import com.cookie.basic.util.FileSaver;

@Service
public class StoreService {

	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private FilePathGenerator filePathGenerator;
	@Autowired
	private FileSaver fileSaver;
	@Autowired
	private StoreFilesMapper storeFilesMapper;

	public int storeInsert(StoreVO storeVO, MultipartFile files) throws Exception {
		File file = filePathGenerator.getUseClassPathResource("upload");
		String fileName = fileSaver.save(file, files);
		String originalName = files.getOriginalFilename();
		System.out.println(fileName);
		int result = storeMapper.storeInsert(storeVO);
		storeVO = storeMapper.sNumSearch(storeVO);
		if (result > 0) {
			StoreFilesVO storeFilesVO = new StoreFilesVO();
			storeFilesVO.setfName(fileName);
			storeFilesVO.setsNum(storeVO.getSsNum());
			storeFilesVO.setoName(originalName);
			result = storeFilesMapper.storeFilesInsert(storeFilesVO);
		}
		return result;
	}

	public int storeUpdate(StoreVO storeVO) throws Exception {
		return storeMapper.storeUpdate(storeVO);
	}

	public List<StoreVO> searchInfo(StoreVO storeVO) throws Exception {
		return storeMapper.searchInfo(storeVO);
	}

	public StoreVO info(StoreVO storeVO) throws Exception {
		return storeMapper.info(storeVO);
	}

	public List<StoreFilesVO> storeFilesSelect(StoreFilesVO storeFilesVO) throws Exception {
		return storeFilesMapper.storeFilesSelect(storeFilesVO);
	}

	public int onUpdate(StoreVO storeVO)throws Exception{
		return storeMapper.onUpdate(storeVO);
	}
	
	public int offUpdate(StoreVO storeVO)throws Exception{
		return storeMapper.offUpdate(storeVO);
	}
	
	public int checkStore(StoreVO storeVO)throws Exception{
		return storeMapper.checkStore(storeVO);
	}
	
	public Map<String,String[]> mapSelect() throws Exception{
		
		//서울
		String[] ab1 = {"종로구", "중구","용산구","성동구", "광진구", "동대문구",
				"중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구",
				"마포구","양천구","강서구","구로구", "금천구", "영등포구", "동작구", "관악구",
				"서초구", "강남구", "송파구", "강동구"};
		//부산
		String[] ab2 = {"중구","서구","동구","영도구","부산진구","동래구","남구",
				"북구","강서구","해운대구","사하구","금정구","연제구","수영구","사양구",
				"기장군"
		};
		//대구
		String[] ab3 = {"중구","동구","서구","남구","북구", "수성구", "달서구",
				"달성군"
		};
		//인천
		String[] ab4 = {"중구","동구","미추홀구","연수구","남동구","부평구","계양구",
				"서구","강화군","옹진군"
		};
		//광주
		String[] ab5 = {"동구","서구","남구","북구","광산구"};
		//대전
		String[] ab6 = {"서구","중구","동구","유성구","대덕구"};
		//세종
		String[] ab7 = {};
		//경기
		String[] ab8 = {"수원시","성남시","안양시","안산시","용인시","광명시",
				"평택시","과천시","오산시","시흥시","군포시","의왕시","하남시",
				"이천시","안성시","김포시","화성시","광주시","여주시","부천시",
				"양평군","고양시","의정부시","동두천시","구리시","남양주시","파주시",
				"양주시","포천시","연천군","가평군"
		};
		//강원
		String[] ab9= {"춘천시","원주시","강릉시","동해시","태백시","속초시",
				"삼척시","홍천군","횡성군","영월군","평창군","철원군","화천군",
				"양구군","인제군","고성군","양양군"
		};
		//충북
		String[] ab10 = {"청주시","충주시","제천시","보은군","옥천군","영동군",
				"진천군","괴산군","음성군","단양군","증평군"
		};
		//충남
		String[] ab11 = {"천안시","공주시","보령시","아산시","서산시","논산시",
				"계릉시","당진시","금산군","부여군","서천군","청양군","홍성군",
				"예산군","태안군"
		};
		//전북
		String[] ab12 = {"전주시","군산시","익산시","정읍시","남원시","김제시",
				"완주군","진안군","무주군","장수군","임실군","순창군","고창군",
				"부안군"
		};
		//전남
		String[] ab13 = {"목포시","여수시","순천시","나주시","광양시","담양군",
				"곡성군","구례군","고흥군","보성군","화순군","장흥군","강진군",
				"해남군","영암군","무안군","함평군","영광군","장성군","완도군",
				"진도군","신안군"
		};
		//경북
		String[] ab14 = {"포항시","경주시","김천시","안동시","구미시","영주시",
				"영천시","상주시","문경시","경산시","군위군","의성군","청송군",
				"영양군","영덕군","청도군","고령군","성주군","칠곡군","예천군",
				"봉화군","을진군","울릉군"
		};
		//경남
		String[] ab15 = {"창원시","진주시","통영시","사천시","김해시","밀양시",
				"거제시","양산시","의령군","함안군","창녕군","고성군","남해군","하동군",
				"산청군","함양군","거창군","합천군"
		};
		//제주
		String[] ab16 = {"제주시","서귀포시"};
		//울산
		String[] ab17 = {"중구","남구","동구","북구","울주군"};
		
		Map<String,String[]> ar = new HashMap<String,String[]>();			
			ar.put("서울", ab1);
			ar.put("부산", ab2);
			ar.put("대구", ab3);
			ar.put("인천", ab4);
			ar.put("광주", ab5);
			ar.put("대전", ab6);
			ar.put("세종", ab7);
			ar.put("경기", ab8);
			ar.put("강원", ab9);
			ar.put("충북", ab10);
			ar.put("충남", ab11);
			ar.put("전북", ab12);
			ar.put("전남", ab13);
			ar.put("경북", ab14);
			ar.put("경남", ab15);
			ar.put("제주", ab16);
			ar.put("울산", ab17);
			
		return ar;
	}
	
	public List<StoreVO> storeList(StoreVO storeVO) throws Exception{
		return storeMapper.storeList(storeVO);
	}
	
	public List<StoreVO> storeList2(StoreVO storeVO) throws Exception{
		return storeMapper.storeList(storeVO);
	}
	
	
}
