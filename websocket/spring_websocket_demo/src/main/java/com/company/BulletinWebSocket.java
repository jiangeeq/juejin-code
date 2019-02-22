package com.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。
 * 类似Servlet的注解mapping。无需在web.xml中配置。
 * configurator = SpringConfigurator.class是为了使该类可以通过Spring注入。
 * @Author jiangpeng
 */
@ServerEndpoint(value = "/webSocket/bulletin")
// 用这个也没办法开启定时任务，最后只能用xml配置定时任务了
//@EnableScheduling
public class BulletinWebSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(BulletinWebSocket.class);

    private  BulletinService bulletinService;
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    public BulletinWebSocket() {
        LOGGER.info("BulletinWebSocket init ");
    }

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<BulletinWebSocket> BULLETIN_WEBSOCKETS = new CopyOnWriteArraySet<BulletinWebSocket>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        // 加入set中
        BULLETIN_WEBSOCKETS.add(this);
        // 新登录用户广播通知
        this.session.getBasicRemote().sendText(applicationContext.getBean(BulletinService.class).getBulletin() + "-" + new Date());
        LOGGER.info("有新连接加入{}！当前在线人数为{}", session, getOnlineCount());
    }

    @OnClose
    public void onClose() {
        BULLETIN_WEBSOCKETS.remove(this);
        LOGGER.info("有一连接关闭！当前在线人数为{}", getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.info("来自客户端的信息：{}", message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.error("发生错误：{}", session.toString());
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * 因为使用了Scheduled定时任务，所以方法不是有参数
     *
     * @throws Exception
     */
//    @Scheduled(cron = "0/2 * * * * ?")
    public void sendMessage() throws IOException {
        // 所有在线用户广播通知
        BULLETIN_WEBSOCKETS.forEach(socket -> {
            try {
                socket.session.getBasicRemote().sendText("定时：" + applicationContext.getBean(BulletinService.class).getBulletin() + "-" + new Date());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static synchronized int getOnlineCount() {
        return BULLETIN_WEBSOCKETS.size();
    }
}