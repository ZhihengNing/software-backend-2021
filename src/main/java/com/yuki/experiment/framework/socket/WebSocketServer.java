package com.yuki.experiment.framework.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuki.experiment.framework.dto.SocketMessageDTO;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.service.PracticeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

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
@Data
public class WebSocketServer {


    private static PracticeService practiceService;

    @Autowired
    public void setPracticeService(PracticeService practiceService){
        WebSocketServer.practiceService=practiceService;
    }

    private static final int NUM=3;
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

    private String teamId="";


    private TeamScores getTeamInfo(String teamId) {
        TeamScores teamScores = new TeamScores();
        List<TeamUserInfo> list = new ArrayList<>();
        if (USERS.containsKey(teamId)) {
            ConcurrentHashMap<Integer, UserSocket> map = USERS.get(teamId);
            teamScores.setTeamId(teamId);
            for (Map.Entry<Integer, UserSocket> entry : map.entrySet()) {
                teamScores.setPractice(entry.getValue().getPractice());
                list.add(new TeamUserInfo(entry.getKey(), entry.getValue().getCorrectNum()));
            }
            teamScores.setTeamUserInfos(list);
            return teamScores;
        }
        return null;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId,
                       @PathParam("courseId") Integer courseId) throws IOException {
        this.session = session;
        this.userId = userId;

        log.info("USERS"+USERS);
        for (Map.Entry<String, ConcurrentHashMap<Integer, UserSocket>> entry : USERS.entrySet()) {
            String teamId = entry.getKey();
            ConcurrentHashMap<Integer, UserSocket> value = entry.getValue();
            if (value.containsKey(userId)) {
                TeamScores teamInfo = getTeamInfo(teamId);
                if (teamInfo != null) {
                    this.sendMessage(teamInfo);
                }
                return;
            }
        }
        addOnlineCount();
        webSocketMap.put(userId, this);
        //String uuid = UUID.randomUUID().toString();

        synchronized (WebSocketServer.class) {
            if (webSocketMap.size() == NUM) {
                String uuid = UUID.randomUUID().toString();
                this.teamId = uuid;
                Practice random = practiceService.random(courseId);
                if (USERS.containsKey(uuid)) {
                    ConcurrentHashMap<Integer, UserSocket> concurrentHashMap = USERS.get(uuid);
                    UserSocket userSocket = new UserSocket(this, 0, random);
                    concurrentHashMap.put(userId, userSocket);
                } else {
                    ConcurrentHashMap<Integer, UserSocket> tempMap = new ConcurrentHashMap<>();
                    List<TeamUserInfo> teamUserInfos = new ArrayList<>();
                    for (Map.Entry<Integer, WebSocketServer> entry : webSocketMap.entrySet()) {
                        UserSocket userSocket = new UserSocket(entry.getValue(), 0, random);
                        tempMap.put(entry.getKey(), userSocket);
                        entry.getValue().setTeamId(uuid);
                        teamUserInfos.add(new TeamUserInfo(entry.getKey(), 0));
                    }
                    TeamScores teamScores = new TeamScores(uuid, random, teamUserInfos);
                    for (Map.Entry<Integer, WebSocketServer> entry : webSocketMap.entrySet()) {
                        entry.getValue().sendMessage(teamScores);
                    }
                    USERS.put(uuid, tempMap);
                }
                webSocketMap.clear();
            }
        }

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

        log.info("teamId"+teamId);
        if (USERS.containsKey(teamId)) {
            //USERS.get(teamId).remove(userId);
            if(USERS.get(teamId).containsKey(userId)) {
                subOnlineCount();
            }
            if (USERS.get(teamId).size() == 0) {
                USERS.remove(teamId);
            }
        }

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
                    TeamScores teamScores = new TeamScores();
                    teamScores.setTeamId(teamId);
                    List<TeamUserInfo>list=new ArrayList<>();
                    for (Map.Entry<Integer, UserSocket> entry : USERS.get(teamId).entrySet()) {
                        teamScores.setPractice(entry.getValue().getPractice());
                        list.add(new TeamUserInfo(entry.getKey(), entry.getValue().getCorrectNum()));
                    }
                    for (Map.Entry<Integer, UserSocket> entry : USERS.get(teamId).entrySet()) {
                        entry.getValue().getWebSocketServer().sendMessage(teamScores);
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

    public void sendMessage(TeamScores teamScores) throws IOException {
        String s = JSONObject.toJSONString(teamScores);
        this.session.getBasicRemote().sendText(s);
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
