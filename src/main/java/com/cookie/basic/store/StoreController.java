package com.cookie.basic.store;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cookie.basic.cart.CartService;
import com.cookie.basic.cart.CartVO;

import com.cookie.basic.member.MemberVO;
import com.cookie.basic.menu.MenuService;
import com.cookie.basic.menu.MenuVO;
import com.cookie.basic.orders.OrdersService;

@Controller
@RequestMapping("/store/**")
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private MenuService menuService;
	@Autowired
	private CartService cartService;

	@Autowired
	private OrdersService ordersService;

	// 지점 등록 폼
	@GetMapping("storeInsert")
	public void storeInsert(HttpSession session) throws Exception {
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		session.setAttribute("member", memberVO);

	}

	// 지점 등록
	@PostMapping("storeInsert")
	public ModelAndView storeInsert(StoreVO storeVO, MultipartFile files, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		int result = storeService.storeInsert(storeVO, files);
		String message = "등록 실패! 자세한 문의는 전화주세요";
		String path = "../";
		if (result > 0) {
			message = "등록 성공!";
			path = "../menu/menuPreset";
			session.setAttribute("store", storeVO);
		}

		mv.addObject("msg", message);
		mv.addObject("path", path);
		mv.setViewName("common/result");

		return mv;
	}

	// 폐업신청
	@GetMapping("storeClose")
	public void storeClose(StoreVO storeVO, HttpSession session) throws Exception {
		storeVO = (StoreVO) session.getAttribute("store");
	}

	@PostMapping("storeClose")
	public ModelAndView storeClose(StoreVO storeVO) throws Exception {
		ModelAndView mv = new ModelAndView();
		int result = storeService.storeClose(storeVO);
		String message = "신청 실패! 자세한 문의는 전화주세요";
		String path = "./";
		if (result > 0) {
			message = "신청 성공!";
			path = "./myInfo";
		}
		mv.addObject("msg", message);
		mv.addObject("path", path);
		mv.setViewName("common/result");
		return mv;
	}

	// 매장 지우기
	@GetMapping("deleteStore")
	public ModelAndView deleteStore(StoreVO storeVO, StoreCloseVO storeCloseVO) throws Exception {
		ModelAndView mv = new ModelAndView();
		int result = storeService.storeUpdateBye(storeCloseVO);
		result = storeService.deleteStore(storeVO);
		String message = "폐업실패! 자세한 문의는 전화주세요";
		String path = "./myInfo";
		if (result > 0) {
			message = "폐업 성공!";
			path = "./myInfo";
		}
		mv.addObject("msg", message);
		mv.addObject("path", path);
		mv.setViewName("common/result");
		return mv;

	}

	// 매장 리스트
	@GetMapping("myInfo")
	public ModelAndView searchInfo(StoreVO storeVO, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		MemberVO memberVO = (MemberVO) session.getAttribute("member");

		storeVO.setMemNum(memberVO.getMemNum());

		List<StoreVO> ar = storeService.searchInfo(storeVO);

		/*
		 * System.out.println(ar.get(0).getsNum());
		 * 
		 * for (StoreVO storeVO2 : ar) { StoreFilesVO storeFilesVO = new StoreFilesVO();
		 * storeFilesVO.setsNum(storeVO2.getsNum()); storeFilesVO =
		 * (StoreFilesVO)storeService.storeFilesSelect(storeFilesVO);
		 * ar2.add(storeFilesVO); System.out.println(storeFilesVO.getfName());
		 * System.out.println(storeFilesVO.getoName()); }
		 */

		mv.addObject("list", ar);
		mv.setViewName("store/myInfo");

		return mv;
	}

	@GetMapping("storeAdmin")
	public ModelAndView storeAdmin(StoreCloseVO storeCloseVO) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<StoreCloseVO> ar = storeService.storeAdmin(storeCloseVO);
		mv.addObject("list", ar);
		mv.setViewName("store/storeAdmin");
		return mv;
	}

	// 매장상세정보//Pos 작동 (ON)
	@GetMapping("myinfoS")
	public ModelAndView myInfoS(StoreVO storeVO, HttpSession session, StoreCloseVO storeCloseVO, MenuVO menuVO, CartVO cartVO) throws Exception {
		ModelAndView mv = new ModelAndView();

		storeVO = storeService.info(storeVO);
		session.setAttribute("store", storeVO);
		storeCloseVO = storeService.storeAdminSelect(storeCloseVO);
		List<CartVO> ar2 = cartService.cartCount(cartVO);
		List<MenuVO> ar = menuService.menuList(menuVO);
		mv.addObject("list", ar);
		mv.addObject("cart", ar2);
		mv.addObject("store", storeVO);
		mv.addObject("close", storeCloseVO);

		return mv;
	}

	@PostMapping("myinfoS")
	public ModelAndView myInfoS(StoreVO storeVO) throws Exception {
		ModelAndView mv = new ModelAndView();
		storeVO.getSsNum();
		int result = storeService.onUpdate(storeVO);

		System.out.println(storeVO.getSsNum());

		String msg = "업데이트 실패";
		String path = "../";
		if (result > 0) {
			msg = "영업시작";

			path = "./storeMyPage?ssNum=" + storeVO.getSsNum();

		}
		mv.addObject("store", storeVO);
		mv.addObject("msg", msg);
		mv.addObject("path", path);
		mv.setViewName("common/result");

		return mv;
	}

	// 점주 페이지
	@GetMapping("storeMyPage")
	public void storeMyPage(StoreVO storeVO, Model model,HttpSession session) throws Exception {
		storeVO = (StoreVO)session.getAttribute("store");
		model.addAttribute("store", storeVO);
	}

	@PostMapping("storeMyPage")
	public ModelAndView storeMyPage(StoreVO storeVO) throws Exception {
		ModelAndView mv = new ModelAndView();
		storeVO.getSsNum();
		int result = storeService.offUpdate(storeVO);
		System.out.println(storeVO.getSsNum());
		String msg = "업데이트 실패";
		String path = "../";
		if (result > 0) {
			msg = "영업종료";
			path = "./myInfo";
		}
		mv.addObject("store", storeVO);
		mv.addObject("msg", msg);
		mv.addObject("path", path);
		mv.setViewName("common/result");

		return mv;

	}

	// 점주 업데이트 폼
	@GetMapping("storeUpdate")
	public ModelAndView storeUpdate(StoreVO storeVO) throws Exception {
		ModelAndView mv = new ModelAndView();

		storeVO = storeService.info(storeVO);
		/* System.out.println(storeVO.getsName()); */

		mv.addObject("store", storeVO);
		mv.setViewName("store/storeUpdate");

		return mv;

	}

	// 점주 정보 업데이트
	@PostMapping("storeUpdate")
	public ModelAndView storeUpdate(StoreVO storeVO, HttpSession session, MultipartFile files) throws Exception {

		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		session.setAttribute("member", memberVO);
		ModelAndView mv = new ModelAndView();
		int result = storeService.storeUpdate(storeVO, files);

		String msg = "업데이트 실패";
		String path = "../";
		if (result > 0) {
			msg = "업데이트 성공";
			path = "./myInfo";
		}

		mv.addObject("msg", msg);
		mv.addObject("path", path);
		mv.setViewName("common/result");

		return mv;
	}

	// 매장 아이디 중복 체크 페이지
	@ResponseBody
	@PostMapping("checkStore")
	public int checkStore(StoreVO storeVO) throws Exception {
		// System.out.println(storeVO.getsName());

		// sName 모든 공백 제거
		String sName = storeVO.getsName();
		sName = sName.replaceAll(" ", "");
		sName = sName.replaceAll("\\p{Z}", "");

		// System.out.println(sName);

		storeVO.setsName(sName);

		int result = storeService.checkStore(storeVO);
		
		if (result > 0) {
			System.out.println("중복");
		} else {
			System.out.println("사용가능");
		}

		return result;
	}

	// 스토어 공지사항
	@GetMapping("storeNotice")
	public void storeNotice() throws Exception {

	}

	@PostMapping("storeNotice")
	public ModelAndView storeNotice(StoreVO storeVO, HttpSession session, MultipartFile files) throws Exception {
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		session.setAttribute("member", memberVO);
		ModelAndView mv = new ModelAndView();

		int result = storeService.storeNotice(storeVO);
		System.out.println(result);
		String msg = "업데이트 실패";
		String path = "../";
		if (result > 0) {
			msg = "업데이트 성공";
			path = "./storeMyPage";
		}

		mv.addObject("msg", msg);
		mv.addObject("path", path);
		mv.setViewName("common/result");

		return mv;
	}

	@GetMapping("storeList")
	public void storeList(Model model) throws Exception {
		Map<String, String[]> ar = storeService.mapSelect();

		model.addAttribute("ar", ar);
	}

	@GetMapping("storeList2")
	public ModelAndView storeList2(String v) throws Exception {
		Map<String, String[]> ar = storeService.mapSelect();
		ModelAndView mv = new ModelAndView();

		String[] arr = ar.get(v);

		mv.addObject("arr", arr);
		mv.addObject("v", v);
		return mv;
	}

	@GetMapping("storeList3")
	public ModelAndView storeList3(String v, String v2, String s) throws Exception {
		ModelAndView mv = new ModelAndView();
		StoreVO storeVO = new StoreVO();
		List<StoreVO> ar = new ArrayList<StoreVO>();

		if (v == null) {
			v = "";
		}
		if (v2 == null) {
			v2 = "";
		}
		if (s == null) {
			s = "";
		}

		storeVO.setMemId(v);

		storeVO.setsTel(s);

		if (v2 == "") {

			ar = storeService.storeList2(storeVO);

		} else {
			storeVO.setsName(v2);
			ar = storeService.storeList(storeVO);
		}

		mv.addObject("v", v);
		mv.addObject("v2", v2);
		mv.addObject("ar", ar);

		return mv;
	}

	// 스토어 굿즈(메뉴판 만드는곳)

	@GetMapping("storeGoods")
	public ModelAndView storeGoods(StoreVO storeVO, String mmNum,HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		MenuVO menuVO = new MenuVO();

		storeVO = storeService.storeGoods(storeVO);
		menuVO.setSsNum(storeVO.getSsNum());
		List<MenuVO> menuVOs = menuService.menuList(menuVO);
		
		if(session.getAttribute("member") != null) {
			MemberVO memberVO = (MemberVO)session.getAttribute("member");
			CartVO cartVO = new CartVO();
			cartVO.setNickname(memberVO.getNickname());
			cartVO.setSsNum(storeVO.getSsNum());
			List<CartVO> ar = cartService.cartList(cartVO);
			mv.addObject("cartVO",ar);
		
		}
		
		mv.addObject("storeVO", storeVO);
		mv.addObject("list", menuVOs);
		return mv;

	}

	@ResponseBody
	@GetMapping("storeResult")
	public ModelAndView storeResult(String mmNum) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		
		MenuVO menuVO = new MenuVO();
		menuVO.setMmNum(Integer.parseInt(mmNum));
		
		menuVO = menuService.menuSelect(menuVO);
		mv.addObject("Detail", menuVO);
		
		return mv;

	}



}
