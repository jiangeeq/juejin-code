package com.example.threadtransaction.service;

import com.example.threadtransaction.dao.UserDao;
import com.example.threadtransaction.domain.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * Created by jiangpeng on 2018/11/6.
 */
@Service
@Transactional
public class UserService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(UserService.class);
    private static Integer num = 0;

    @Autowired
    UserDao userDao;
    @Resource
    private DataSourceTransactionManager txManager;

    public void insert1() throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (num <= 10) {
                        processEachPlan();
                    }
                } catch (Exception e) {
                    Logger.info("异常信息：" + e.toString());
                }
            }
        }).start();
    }

    /**
     * 加上@Transactional(propagation = Propagation.REQUIRES_NEW)有效果，
     * 但是最后一条插入的记录没有回滚
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insert2() throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (num <= 10) {
                        processEachPlan();
                    }
                } catch (Exception e) {
                    Logger.info("异常信息：" + e.toString());
                }
            }
        }).start();
    }

    /**
     * 注解起到了事务控制
     *
     * @throws SQLException
     */
    public void insert3() throws SQLException {
        while (num <= 10) {
            processEachPlan();
        }
    }

    /**
     * 编程式事务管理线程
     *
     * @throws SQLException
     */
    public void insert4() throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                //事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；获取事务状态后，Spring根据传播行为来决定如何开启事务
                TransactionStatus status = txManager.getTransaction(def);
                try {
                    while (num <= 10) {
                        processEachPlan();
                    }
                } catch (Exception e) {
                    Logger.info("异常信息：" + e.toString());
                    txManager.rollback(status); // 回滚事务
                }
                txManager.commit(status); // 提交事务
            }
        }).start();
    }


    @Async  // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行
    public void executeAsyncTask() throws SQLException, InterruptedException {
        while (num <= 100) {
            processEachPlan();
            System.out.println(Thread.currentThread().getName() + "====>num = " + num);
            Thread.sleep(100);
        }
    }


    public void processEachPlan() throws SQLException {
        User user = new User();
        user.setName("李白" + num);
        userDao.add(user);
        if (num == 50) {
            int b = num / 0;
        }
        num++;
    }
}
