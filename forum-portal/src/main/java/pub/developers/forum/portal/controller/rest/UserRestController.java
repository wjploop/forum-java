package pub.developers.forum.portal.controller.rest;

import org.springframework.web.bind.annotation.*;
import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.user.*;
import pub.developers.forum.api.service.UserApiService;
import pub.developers.forum.api.service.ValidCodeService;
import pub.developers.forum.common.constant.Constant;
import pub.developers.forum.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/29
 * @desc
 **/
@RestController
@RequestMapping("/user-rest")
public class UserRestController {

    @Resource
    private UserApiService userApiService;

    @Resource
    private ValidCodeService validCodeService;

    @PostMapping("/send-code")
    public ResultModel<String> sendCode(@RequestBody ValidCodeRequest request, HttpServletRequest servletRequest) {
        request.setIp(WebUtil.requestIp(servletRequest));
        request.setUa(WebUtil.requestUa(servletRequest));

        return validCodeService.sendMailCode(request);
    }

    @PostMapping("/register")
    public ResultModel<String> register(@RequestBody UserRegisterRequest request, HttpServletRequest servletRequest, HttpServletResponse response) {
        request.setIp(WebUtil.requestIp(servletRequest));
        request.setUa(WebUtil.requestUa(servletRequest));
        ResultModel<String> resultModel = userApiService.register(request);

        WebUtil.cookieAddSid(response, resultModel.getData());

        return resultModel;
    }

    @PostMapping("/login")
    public ResultModel<String> login(@RequestBody UserEmailLoginRequest request, HttpServletRequest servletRequest, HttpServletResponse response) {
        request.setIp(WebUtil.requestIp(servletRequest));
        request.setUa(WebUtil.requestUa(servletRequest));
        ResultModel<String> resultModel =  userApiService.emailLogin(request);

        WebUtil.cookieAddSid(response, resultModel.getData());

        return resultModel;
    }

    @PostMapping("/update-info")
    public ResultModel updateInfo(@RequestBody UserUpdateInfoRequest updateInfoRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.updateInfo(updateInfoRequest);
    }

    @PostMapping("/update-pwd")
    public ResultModel updatePwd(@RequestBody UserUpdatePwdRequest updatePwdRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.updatePwd(updatePwdRequest);
    }

    @PostMapping("/follow/{followed}")
    public ResultModel follow(@PathVariable("followed") Long followed, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.follow(followed);
    }

    @PostMapping("/cancel-follow/{followed}")
    public ResultModel cancelFollow(@PathVariable("followed") Long followed, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.cancelFollow(followed);
    }
}
