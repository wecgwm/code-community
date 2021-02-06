package com.code.community.test.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.community.common.model.PageInfo;
import com.code.community.test.model.vo.AnswerVo;
import com.code.community.test.model.vo.QuestionVo;
import com.code.community.user.model.po.User;

import java.util.List;

public interface AnswerRecordService {
    /**
     * 保存答题记录
     * @param answerVos 答案List
     */
    void saveAnswerRecord(List<AnswerVo> answerVos);

    /**
     * 获取答题记录
     * @param pageInfo 分页信息
     * @param user 用户
     * @return
     */
    Page<QuestionVo> getQuestionRecord(PageInfo pageInfo, User user);
}
