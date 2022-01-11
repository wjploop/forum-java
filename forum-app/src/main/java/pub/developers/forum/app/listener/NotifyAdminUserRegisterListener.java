package pub.developers.forum.app.listener;

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

        // 发送消息通知，邮箱连接
        messageService.send(Message.builder()
                .channel(MessageChannelEn.MAIL)
                .type(MessageTypeEn.USER_REGISTER_NOTIFY_ADMIN)
                .sender(IdValue.builder()
                        .id("wjploop@163.com")
                        .type(IdValueTypeEn.EMAIL)
                        .build())
                .receiver(
                        IdValue.builder()
                        .id(user.getEmail())
                        .type(IdValueTypeEn.EMAIL)
                        .build())
                .title("欢迎来到英雄联盟")
                .contentType(MessageContentTypeEn.TEXT)
                .read(MessageReadEn.NO)
                .content("请点击该链接确认注册 www.baidu.com")
                .build());
        //
    }
}
