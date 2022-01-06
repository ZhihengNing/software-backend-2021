package com.yuki.experiment.framework.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuki.experiment.framework.dto.SocketMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author zhengkai.blog.csdn.net
 */
@ServerEndpoint("/server/{teamId}/{userId}")
@Component
@Slf4j
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    //private static final ConcurrentHashMap<Integer, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, ConcurrentHashMap<Integer, WebSocketServer>> allUserMap
            = new ConcurrentHashMap<>();

    /**
     * 每一个队伍的分数
     */
    private static final ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> scores
            = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private Integer userId;
    /**
     * 团队Id
     */
    private String teamId;
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("teamId")String teamId,
                       @PathParam("userId") Integer userId) {
        //String uuid = UUID.randomUUID().toString();
        this.teamId = teamId;
        this.session = session;
        this.userId = userId;
        if (allUserMap.containsKey(teamId)) {
            ConcurrentHashMap<Integer, WebSocketServer> concurrentHashMap = allUserMap.get(teamId);
            concurrentHashMap.put(userId, this);
        } else {
            ConcurrentHashMap<Integer, WebSocketServer> tempMap = new ConcurrentHashMap<>();
            tempMap.put(userId, this);
            allUserMap.put(teamId, tempMap);
            addOnlineCount();
        }

        if(scores.containsKey(teamId)){
            ConcurrentHashMap<Integer, Integer> concurrentHashMap = scores.get(teamId);
            concurrentHashMap.put(userId, 0);
        }else{
            ConcurrentHashMap<Integer, Integer> tempMap = new ConcurrentHashMap<>();
            tempMap.put(userId, 0);
            scores.put(teamId, tempMap);
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
        if (allUserMap.containsKey(teamId)) {
            allUserMap.get(teamId).remove(userId);
            subOnlineCount();
        }
        if (allUserMap.get(teamId).size() == 0) {
            allUserMap.remove(teamId);
        }
        if(scores.containsKey(teamId)){
            scores.get(teamId).remove(userId);
        }if(scores.get(teamId).size()==0){
            scores.remove(teamId);
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *这里参数只能是string
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

                if (allUserMap.containsKey(teamId) && allUserMap.get(teamId).containsKey(userId)) {
                    if(!scores.containsKey(teamId)) {
                        scores.put(teamId,new ConcurrentHashMap<>());
                        scores.get(teamId).put(userId, correctNum);
                    }
                    else {
                        scores.get(teamId).put(userId, correctNum);
                        List<TeamScores>list=new ArrayList<>();
                        for(Map.Entry<Integer,Integer>entry:scores.get(teamId).entrySet()){
                            TeamScores teamScores=new TeamScores();
                            teamScores.setTeamId(teamId);
                            teamScores.setUserId(entry.getKey());
                            teamScores.setCorrectNum(entry.getValue());
                            list.add(teamScores);
                        }
                        for (Map.Entry<Integer, WebSocketServer> entry : allUserMap.get(teamId).entrySet()) {
                            entry.getValue().sendMessage(list.toString());
                        }
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
    public static void sendInfo(String message, String userId) throws IOException {
//        log.info("发送消息到:" + userId + "，报文:" + message);
//        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
//            webSocketMap.get(userId).sendMessage(message);
//        } else {
//            log.error("用户" + userId + ",不在线！");
//        }
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
