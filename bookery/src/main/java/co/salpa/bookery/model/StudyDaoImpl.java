package co.salpa.bookery.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.salpa.bookery.model.entity.StudyVo;

@Repository
public class StudyDaoImpl implements StudyDao {

	@Autowired
	DataSource dataSource;
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public List<StudyVo> selectAll() throws SQLException { // ��� ���͵����� ��ȸ
		// TODO Auto-generated method stub
		List<StudyVo> list = new ArrayList<StudyVo>();
		try (SqlSession session = sqlSessionFactory.openSession()) {
			list = session.selectList("study.selectAll");
		} // try
		return list;
	}// selectAll

	@Override
	public StudyVo selectOne(int key) throws SQLException { // Ư�� ���͵� ��ȸ
		// TODO Auto-generated method stub
		StudyVo bean = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			bean = session.selectOne("study.selectOne", key);
		} // try
		return bean;
	}// selectOne

	@Override
	public void insertOne(StudyVo bean) throws SQLException { // ���͵� �߰�
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.insert("study.insertOne", bean);
		} // try
	}// insertOne

}// classEnd
