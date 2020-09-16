package co.salpa.bookery.find.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.salpa.bookery.model.BookDao;
import co.salpa.bookery.model.StudyDao;
import co.salpa.bookery.model.TocDao;
import co.salpa.bookery.model.entity.BookVo;
import co.salpa.bookery.model.entity.StudyVo;
import co.salpa.bookery.model.entity.TocVo;

@Controller
@RequestMapping("/find")
public class FindAddController {
	Logger log = Logger.getGlobal();
	@Autowired
	private TocDao tocDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private StudyDao studyDao;
	
/***************************�˻��� å �󼼺��⿡�� ������ ��� ������ ��.**********************************/
	@RequestMapping("/put") //�� ���� ��� ��� Book, Toc, Study ���̺� å �Է�
	public ModelAndView insertChapters(@ModelAttribute BookVo book, @ModelAttribute StudyVo study, String chapters)
			throws Exception {
		// Book talble�� å �����Է�
		bookDao.insertOne(book);


		// Toc���� ���̺� �Է�
		if (tocDao.selectOne(book.getBid()).size() != 0) {
			log.info("���� �Է� : fail ");
			//toc�� ����å�� ������ ������ insert��������
		} else {
			//**************���߿� TocDaoImpl�� ó���� �Ű� �� Ʈ����� ó�� �ϱ�***********************
			String[] tmp = chapters.split("\n");
			System.out.println(tmp.toString());
			for (String chapter : tmp) {
				if (chapter.trim().equals("")) { // ��������
					continue;
				} else {
					tocDao.insertOne(new TocVo(book.getBid(), chapter.trim()));//������ �ش������ å��ȣ
				} // if
			} // for
			
			log.info("���� �Է� !! success");
			// test�� user_id
		}//out if
		
//���� �߰����� 
//		study.setUser_id(uesr_test_cnt);
//		study.setBook_bid(book.getBid());
//		studyDao.insertOne(study);
//		uesr_test_cnt++;

		return null;
	}// insertChapters
	

}//classEnd
