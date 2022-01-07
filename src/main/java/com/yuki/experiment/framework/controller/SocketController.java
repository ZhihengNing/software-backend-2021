package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.dto.SocketMessageDTO;
import com.yuki.experiment.framework.socket.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * WebSocketController
 * @author zhengkai.blog.csdn.net
 */
@RestController
@Api(tags="不要用")
@RequestMapping("/api/socket")
public class SocketController {

    @GetMapping("index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("请求成功");
    }

    @GetMapping("page")
    public ModelAndView page(){
        return new ModelAndView("websocket");
    }

    @RequestMapping(value = "/push/{toUserId}",method = RequestMethod.GET)
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        //WebSocketServer.sendInfo(message,toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }


    @ApiOperation("发送消息给某人")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult pushToSomeOne(@RequestBody SocketMessageDTO message) throws EncodeException, IOException {
        if(message.getUserId()==null){
            return CommonResult.failed("用户Id不能为空");
        }
        WebSocketServer.sendInfo(message);
        return CommonResult.success();
    }

}

