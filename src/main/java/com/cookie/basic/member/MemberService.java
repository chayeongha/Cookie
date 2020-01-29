package com.cookie.basic.member;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.cookie.basic.store.StoreVO;
import com.cookie.basic.util.FilePathGenerator;
import com.cookie.basic.util.FileSaver;
import com.cookie.basic.util.Pager;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberFilesMapper memberFilesMapper;
	
	@Autowired
	private FilePathGenerator filePathGenerator;
	
	@Autowired 
	private FileSaver fileSaver;
	
	//검증1
	public boolean memberJoinValidate(MemberVO memberVO, BindingResult bindingResult)throws Exception{
		//트루일땐 에러 펄스면 에러x
		boolean check= false;//비번이 일치하는지검증
		boolean check2= false;//아이디가 중복인지검증
		boolean check3= false;//닉네임이 중복인지 검증
		//검증결과
		if(bindingResult.hasErrors()) {
			check=true;
		}
	
		//비번이 일치하는지 검증
		if(!memberVO.getPw().equals(memberVO.getPwCheck())) {
			check=true;
			//form의 path명, 출력하고싶은 properties의키 
			bindingResult.rejectValue("pwCheck", "memberVO.pw.notEqual");
		}//잘나옴.
		
		MemberVO memberVO2 = new MemberVO();
		//id가 중복인지 검증
		memberVO2 = memberMapper.memberIdCheck(memberVO);
		
		if(memberVO2 != null) {
			check2= true;
			bindingResult.rejectValue("memId", "memberVO.memId.idCheck");
		}
		//닉네임이 중복인지 검증
		MemberVO memberVO3 = new MemberVO();
		
		memberVO3= memberMapper.memberNickCheck(memberVO);
		if(memberVO3 !=null) {
			check3=true;
			bindingResult.rejectValue("nickname", "memberVO.nickname.nickCheck");
		}
		
		return check;
	}
	
	//프론트 아이디중복검사
	public MemberVO memberIdCheck(MemberVO memberVO)throws Exception {
		return memberMapper.memberIdCheck(memberVO);
	}
	
	//프론트 닉네임중복검사
	public MemberVO memberNickCheck(MemberVO memberVO)throws Exception {
		return memberMapper.memberNickCheck(memberVO);
	}
	
	
	//회원가입
	public int memberJoin(MemberVO memberVO, MultipartFile files)throws Exception{
		File file = filePathGenerator.getUseClassPathResource("upload");
		String fileName=fileSaver.save(file, files);
		
		//System.out.println(fileName); 아주잘나옴~

		int result = memberMapper.memberJoin(memberVO);
		
		boolean check= false;
		
		//로그인할때 프로필사진을 넣었을 때 파일테이블에 행추가o 아닐시에는 행추가x 
		if(files.getSize()>0) {
			check=true;			
		}
		
		if(check) {
			MemberFilesVO memberFilesVO2 = new MemberFilesVO();
			memberFilesVO2.setMemNum(memberVO.getMemNum());
			memberFilesVO2.setFname(fileName);
			memberFilesVO2.setOname(files.getOriginalFilename());
			
			result =memberFilesMapper.memberFilesInsert(memberFilesVO2);
		}
		return result;
	}
	
	//네이버 회원가입
	public int naverJoin(MemberVO memberVO) throws Exception{
		return memberMapper.naverJoin(memberVO);
	}
	
	//네이버(로그인)
	public MemberVO memberNaver(MemberVO memberVO) throws Exception{
		return memberMapper.memberNaver(memberVO);
	}
	
	//카카오 회원가입
	public int kakaoJoin(MemberVO memberVO) throws Exception{
		return memberMapper.kakaoJoin(memberVO);
	}
		
	//카카오(로그인)
	public MemberVO memberKakao(MemberVO memberVO) throws Exception{
		return memberMapper.memberKakao(memberVO);
	}
	
	
	//로그인
	public MemberVO memberLogin(MemberVO memberVO)throws Exception{	
		return memberMapper.memberLogin(memberVO);
	}
	
	//수정
	public int memberUpdate(MemberVO memberVO,MultipartFile files)throws Exception{
		
		File file = filePathGenerator.getUseClassPathResource("upload");
		
		String fileName=fileSaver.save(file, files);		
		
		System.out.println(fileName);

		int result = memberMapper.memberUpdate(memberVO);
		
		//System.out.println(memberVO.getMemNum()); 잘나옴.
		
		MemberFilesVO memberFilesVO = new MemberFilesVO();
		
		//이미지를 안넣고 회원가입을 했을때 
		//나중에 이미지를 넣으면 fnum없어서 null 오류가 뜨기때문에 
		//fnum이 없을 때 파일을 인서트하는 조건을 줌.
		if(memberVO.getFnum() ==null) {
			memberFilesVO.setMemNum(memberVO.getMemNum());
			memberFilesVO.setFname(fileName);
			memberFilesVO.setOname(files.getOriginalFilename());
			
			result=memberFilesMapper.memberFilesInsert(memberFilesVO);
		}else {
		//fnum이 있을때 파일을 업데이트하는 조건을 줌.
		memberFilesVO.setMemNum(memberVO.getMemNum());
		memberFilesVO.setFnum(memberVO.getFnum());
		memberFilesVO.setFname(fileName);
		memberFilesVO.setOname(files.getOriginalFilename());
		
		result =memberFilesMapper.memberFilesUpdate(memberFilesVO);
		}
		return  result;
	}
	
	//마이페이지에서 회원 탈퇴 
	public int memberDelete(MemberVO memberVO)throws Exception{
		return memberMapper.memberDelete(memberVO);
	}
	
}
