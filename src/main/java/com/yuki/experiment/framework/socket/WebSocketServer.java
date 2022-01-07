package com.yuki.experiment.framework.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuki.experiment.framework.controller.UserController;
import com.yuki.experiment.framework.dto.SocketMessageDTO;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.service.PracticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author zhengkai.blog.csdn.net
 */
@ServerEndpoint("/server/{userId}/{courseId}")
@Component
@Slf4j
public class WebSocketServer {

    private static MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        WebSocketServer.mongoTemplate = mongoTemplate;
    }

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final ConcurrentHashMap<Integer, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, ConcurrentHashMap<Integer, UserSocket>> USERS
            = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private Integer userId;

    private String teamId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId,
                       @PathParam("courseId") Integer courseId) throws IOException {
        webSocketMap.put(userId, this);
        //String uuid = UUID.randomUUID().toString();
        this.session = session;
        this.userId = userId;

        synchronized (WebSocketServer.class) {
            if (webSocketMap.size() == 3) {
                String uuid = UUID.randomUUID().toString();
                this.teamId=uuid;
                if (USERS.containsKey(uuid)) {
                    ConcurrentHashMap<Integer, UserSocket> concurrentHashMap = USERS.get(uuid);
                    UserSocket userSocket = new UserSocket(this, 0);
                    concurrentHashMap.put(userId, userSocket);
                } else {
                    ConcurrentHashMap<Integer, UserSocket> tempMap = new ConcurrentHashMap<>();
                    JSONObject json = new JSONObject();
                    List<TeamScores> teamScoresList = new ArrayList<>();
                    for (Map.Entry<Integer, WebSocketServer> entry : webSocketMap.entrySet()) {
                        UserSocket userSocket = new UserSocket(entry.getValue(), 0);
                        tempMap.put(entry.getKey(), userSocket);
                        TeamScores teamScores = new TeamScores(uuid, entry.getKey(), 0);
                        teamScoresList.add(teamScores);
                    }
                    json.put("team", teamScoresList);

                    System.out.println(mongoTemplate);
                    Criteria criteria = Criteria.where("courseId").is(courseId);
                    Query query = new Query(criteria);
                    List<Practice> practices = mongoTemplate.find(query, Practice.class, "practice");
                    json.put("problem", practices);
                    for (Map.Entry<Integer, WebSocketServer> entry : webSocketMap.entrySet()) {
                        entry.getValue().sendMessage(json.toJSONString());
                        log.info("发送信息"+json);
                    }
                    USERS.put(uuid, tempMap);
                }
                webSocketMap.clear();
            }
        }
        //查看当前人数
        addOnlineCount();

        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + userId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {

        if (USERS.containsKey(teamId)) {
            USERS.get(teamId).remove(userId);
            subOnlineCount();
        }
//        if (USERS.get(teamId).size() == 0) {
//            USERS.remove(teamId);
//        }
//        if (allUserMap.containsKey(teamId)) {
//            allUserMap.get(teamId).remove(userId);
//        }
//        if (allUserMap.get(teamId).size() == 0) {
//            allUserMap.remove(teamId);
//        }
//        if (scores.containsKey(teamId)) {
//            scores.get(teamId).remove(userId);
//        }
//        if (scores.get(teamId).size() == 0) {
//            scores.remove(teamId);
//        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * 这里参数只能是string
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + userId + ",报文:" + message);
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                String teamId = jsonObject.getString("teamId");
                Integer userId = jsonObject.getInteger("userId");
                int correctNum = jsonObject.getIntValue("correctNum");
                if (USERS.containsKey(teamId) && USERS.get(teamId).containsKey(userId)) {
                    UserSocket userSocket = USERS.get(teamId).get(userId);
                    userSocket.setCorrectNum(correctNum);
                    List<TeamScores> list = new ArrayList<>();
                    for (Map.Entry<Integer, UserSocket> entry : USERS.get(teamId).entrySet()) {
                        TeamScores teamScores = new TeamScores();
                        teamScores.setTeamId(teamId);
                        teamScores.setUserId(entry.getKey());
                        teamScores.setCorrectNum(entry.getValue().getCorrectNum());
                        list.add(teamScores);
                    }
                    for (Map.Entry<Integer, UserSocket> entry : USERS.get(teamId).entrySet()) {
                        entry.getValue().getWebSocketServer().sendMessage(list.toString());
                        log.info("发送消息"+list);
                    }
                } else {
                    log.error("请求的userId:" + userId + "不在该服务器上");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(List<TeamScores> message, Integer userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (userId != null && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message.toString());
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    public static void sendProblem(JSONObject message, Integer userId) throws IOException {

        log.info("发送消息到:" + userId + "，报文:" + message);
        if (userId != null && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message.toJSONString());
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    public static void sendInfo(SocketMessageDTO messageDTO) throws EncodeException, IOException {
//        Integer userId = messageDTO.getUserId();
//        JSONObject message = messageDTO.getMessage();
//        log.info("发送消息到:" + userId + "，报文:" + message);
//        if (webSocketMap.containsKey(userId)) {
//            webSocketMap.get(userId).sendMessage(message.toJSONString());
//        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
