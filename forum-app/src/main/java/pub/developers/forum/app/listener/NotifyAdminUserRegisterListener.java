package pub.developers.forum.app.listener;

import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.stereotype.Component;
import pub.developers.forum.common.enums.*;
import pub.developers.forum.common.support.EventBus;
import pub.developers.forum.domain.entity.Message;
import pub.developers.forum.domain.entity.User;
import pub.developers.forum.domain.entity.value.IdValue;
import pub.developers.forum.domain.service.MessageService;

import javax.annotation.Resource;

/**
 * @author Qiangqiang.Bian
 * @create 2020/12/4
 * @desc
 **/
@Component
public class NotifyAdminUserRegisterListener extends EventBus.EventHandler<User> {

    @Resource
    private MessageService messageService;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.USER_REGISTER;
    }

    @Override
    public void onMessage(User user) {

        // 发送消息通知
        messageService.send(Message.builder()
                .channel(MessageChannelEn.STATION_LETTER)
                .type(MessageTypeEn.USER_REGISTER_NOTIFY_ADMIN)
                .sender(IdValue.SystemId)
                .receiver(IdValue.SystemId)
                .title("新用户注册了")
                .contentType(MessageContentTypeEn.TEXT)
                .read(MessageReadEn.NO)
                .content(user.toString())
                .build());

        //
    }
}
