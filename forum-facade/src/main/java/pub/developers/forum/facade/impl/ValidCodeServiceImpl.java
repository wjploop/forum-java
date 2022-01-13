package pub.developers.forum.facade.impl;

import org.springframework.stereotype.Service;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.user.ValidCodeRequest;
import pub.developers.forum.api.service.ValidCodeService;
import pub.developers.forum.common.enums.*;
import pub.developers.forum.common.support.CheckUtil;
import pub.developers.forum.domain.entity.Message;
import pub.developers.forum.domain.entity.value.IdValue;
import pub.developers.forum.domain.service.CacheService;
import pub.developers.forum.domain.service.MessageService;
import pub.developers.forum.facade.support.ResultModelUtil;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class ValidCodeServiceImpl implements ValidCodeService {

    @Resource
    private MessageService messageService;

    @Resource
    private CacheService cacheService;

    @Override
    public ResultModel<String> sendMailCode(ValidCodeRequest request) {
        // todo 检查该 ip 是否异常

        // 电话、邮箱
        String mail = request.getEmail();

        CheckUtil.checkParamToast(request.getEmail(), "email");

        int i = new Random().nextInt(1000000);
        String code = String.format("%06d", i);

        messageService.send(Message.builder()
                .channel(MessageChannelEn.MAIL)
                .type(MessageTypeEn.USER_REGISTER_SEND_CODE)
                .sender(IdValue.SystemId)
                .read(MessageReadEn.NO)
                .contentType(MessageContentTypeEn.TEXT)
                .receiver(IdValue.builder()
                        .id(mail)
                        .type(IdValueTypeEn.EMAIL)
                        .build())
                .title("验证码")
                .content("来自萝卜平，验证码：" + code + ", 有效期3分钟")
                .build());

        cacheService.setAndExpire(CacheBizTypeEn.VALID_CODE, mail, code, 60L * 3);

        return ResultModelUtil.success("code");
    }
}
