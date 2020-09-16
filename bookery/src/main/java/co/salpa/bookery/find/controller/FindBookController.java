package co.salpa.bookery.find.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import co.salpa.bookery.model.BookDao;
import co.salpa.bookery.model.StudyDao;
import co.salpa.bookery.model.TocDao;
import co.salpa.bookery.model.entity.BookVo;
import co.salpa.bookery.model.entity.StudyVo;
import co.salpa.bookery.model.entity.TocVo;


@Controller
@RequestMapping("/find")
public class FindBookController {
	
	static private int uesr_test_cnt = 1;//test�� userid
	@Autowired
	private TocDao tocDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private StudyDao studyDao;
	
	
/***************************�˻���Ͽ��� å ������ �� å �󼼺��� �������� �̵�**********************************/
	
	@RequestMapping("/book")
	public ModelAndView findBook(int bid) {
		return new ModelAndView("/find/findBook","bid",bid);
	}
	
	
/*************************** �ӽ� : book ���̺� ��ȸ �� ���� ��ȸ ������**********************************/
	@RequestMapping("/mylib")
	public ModelAndView showBooks() throws Exception {
		return new ModelAndView("/find/mybooks", "books", bookDao.selectAll());
	}//showBooks
	
	@RequestMapping("/chapters")
	public ModelAndView showChapters(@RequestParam int bid, HttpServletRequest request) throws Exception {
	//	req.setAttribute("book", bookDao.selectOne(bid));
		request.setAttribute("book", bookDao.selectOne(bid));
	//	System.out.println(bookDao.selectOne(bid));
		return new ModelAndView("/find/mybookchapters", "bookChapters", tocDao.selectOne(bid));
	}//

}//ClassEnd
	
	
