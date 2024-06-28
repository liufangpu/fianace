package com.example.demo.finance.websocket;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.finance.dto.AssetDTO;
import com.example.demo.finance.dto.AssetPriceDTO;
import com.example.demo.finance.service.AssetServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 资产变动websocket处理器
 */
@Component
public class AssetWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(AssetWebSocketHandler.class);
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Autowired
    private AssetServiceI assetService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("Session added: {}", session.getId());
        // 获取整个查询字符串
        String myParam = session.getUri().getQuery();
        Map<String, String> queryParams = parseQueryParams(myParam);
        String account = queryParams.get("account");
        //如果传递了账户参数，记录一下资产信息供后续使用
        if (StringUtils.hasLength(account)){
            log.info("Session account: {}", account);
            List<AssetDTO> byAccount = assetService.getByAccount(account);
            session.getAttributes().put("assets", byAccount);
        }
        try {
            session.sendMessage(new TextMessage("Connection established"));
        } catch (IOException e) {
            log.error("Error when sending welcome message: ", e);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received message: {}", message.getPayload());
        // Echo the message back to all connected sessions
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                try {
                    s.sendMessage(new TextMessage("Echo: " + message.getPayload()));
                } catch (IOException e) {
                    log.error("Error when echoing message back: ", e);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        log.info("Session removed: {}", session.getId());
    }

    public void broadcast(AssetPriceDTO assetPriceDTO) {
        log.info("Broadcasting message: {}", assetPriceDTO);
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                //当前session用户指定账户的资产列表
                List<AssetDTO> assets =(List<AssetDTO>) session.getAttributes().get("assets");
                //如果指定了账户才需要进行判断需不需要推送，否则直接推送
                if (CollectionUtil.isNotEmpty(assets)){
                    String symbol = assetPriceDTO.getSymbol();
                    List<String> list = assets.stream().map(AssetDTO::getSymbol).toList();
                    boolean containsSymbol = list.contains(symbol);
                    //如果这个消息的symbol是属于该用户的资产，则进行通知
                    if (containsSymbol) {
                        try {
                            session.sendMessage(new TextMessage( JSONUtil.toJsonStr(assetPriceDTO)));
                        } catch (IOException e) {
                            log.error("Failed to broadcast message to session: {}", session.getId(), e);
                            sessions.remove(session);  // Remove session if we cannot communicate with it
                        }
                    }
                }else {
                    try {
                        session.sendMessage(new TextMessage(JSONUtil.toJsonStr(assetPriceDTO)));
                    } catch (IOException e) {
                        log.error("Failed to broadcast message to session: {}", session.getId(), e);
                        sessions.remove(session);  // Remove session if we cannot communicate with it
                    }
                }


            }
        }
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> queryParams = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] parts = param.split("=");
                if (parts.length > 1) {
                    queryParams.put(parts[0], parts[1]);
                }
            }
        }
        return queryParams;
    }
}
