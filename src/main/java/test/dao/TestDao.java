package test.dao;

import com.fekpal.service.impl.SauAuditServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;


/**
 * Created by APone on 2018/2/17 0:17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mybatis-config.xml"})
/*@Rollback
@Transactional(transactionManager = "txManager")*/
public class TestDao {
    private String resource="mybatis-config.xml";
    private InputStream inputStream;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    @Autowired
    private SauAuditServiceImpl sauAuditServiceImpl;


   // @Before
    public void before(){
        inputStream=TestDao.class.getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession=sqlSessionFactory.openSession();
    }
    //@After
    public void after(){
        sqlSession.close();
    }

    @Test
    public void testXmlQueryById() {
/*        LikeOrgMapper studentDao=sqlSession.getMapper(LikeOrgMapper.class);
        LikeOrg likeOrg=studentDao.selectByPrimaryKey(1);
        System.out.println(likeOrg);*/
    }

}
