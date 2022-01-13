package pub.developers.forum.api.service;


import pub.developers.forum.api.model.ResultModel;
import pub.developers.forum.api.request.user.ValidCodeRequest;

public interface ValidCodeService {
    ResultModel<String> sendMailCode(ValidCodeRequest request);

}
