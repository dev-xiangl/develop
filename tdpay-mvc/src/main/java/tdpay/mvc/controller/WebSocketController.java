package tdpay.mvc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import tdpay.mvc.common.WebSocketConstants;

@RestController
public class WebSocketController extends BaseController {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(WebSocketController.class);

    @MessageMapping(WebSocketConstants.Message.GET_STATUS)
    @SendTo(WebSocketConstants.SIMPLE_BLOKER)
    public String getStatus(SimpMessageHeaderAccessor accessor, @Payload Message message) throws Exception {
        logger.debug("SimpMessageHeaderAccessor: {}", accessor);
        logger.debug("getId: {}", accessor.getId());
        logger.debug("getSessionId: {}", accessor.getSessionId());
        logger.debug("getDestination: {}", accessor.getDestination());

        String sendMessage = "";
        logger.debug("sendMessage: {}", sendMessage);
        return sendMessage;
    }

    @MessageMapping(WebSocketConstants.Message.SET_STATUS)
    @SendTo(WebSocketConstants.SIMPLE_BLOKER)
    public String setStatus(SimpMessageHeaderAccessor accessor, @Payload Message message) throws Exception {
        logger.debug("SimpMessageHeaderAccessor: {}", accessor);
        logger.debug("getId: {}", accessor.getId());
        logger.debug("getSessionId: {}", accessor.getSessionId());
        logger.debug("getDestination: {}", accessor.getDestination());

        String sendMessage = "";
        logger.debug("sendMessage: {}", sendMessage);
        return sendMessage;
    }
}
