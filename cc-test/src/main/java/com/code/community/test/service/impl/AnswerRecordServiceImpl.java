package com.code.community.test.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.community.comment.base.constant.FiledConstant;
import com.code.community.comment.base.constant.NumberConstant;
import com.code.community.comment.base.constant.RedisConstant;
import com.code.community.comment.base.exception.BaseException;
import com.code.community.comment.base.exception.CRUDException;
import com.code.community.comment.base.exception.CRUDExceptionType;
import com.code.community.common.auth.UserContextHolder;
import com.code.community.common.model.PageInfo;
import com.code.community.common.service.RedisService;
import com.code.community.test.dao.AnswerRecordMapper;
import com.code.community.test.model.po.Answer;
import com.code.community.test.model.po.AnswerRecord;
import com.code.community.test.model.vo.AnswerVo;
import com.code.community.test.model.vo.QuestionVo;
import com.code.community.test.service.AnswerRecordService;
import com.code.community.test.service.AnswerService;
import com.code.community.test.service.QuestionService;
import com.code.community.user.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@CacheConfig(cacheNames = "answerRecord")
public class AnswerRecordServiceImpl extends ServiceImpl<AnswerRecordMapper, AnswerRecord> implements AnswerRecordService {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RedisService redisService;

    //TODO 如果之前存在答题记录，需要对之前的记录进行更新或者删除。
    @Override
    public void saveAnswerRecord(List<AnswerVo> answerVos) {
        User user = UserContextHolder.getUser();
        List<AnswerRecord> answerRecords = new ArrayList<AnswerRecord>(NumberConstant.TEN);
        answerVos.forEach(answerVo -> {
            if (Objects.nonNull(answerVo)) {
                AnswerRecord answerRecord = new AnswerRecord(answerVo.getId(), user.getId());
                answerRecords.add(answerRecord);
            }
        });
        boolean save = this.saveBatch(answerRecords);
        if (save) {
            redisService.del(RedisConstant.ANSWER_RECORD_PREFIX + user.getId());
            return;
        }
        throw new CRUDException(CRUDExceptionType.INSERT_FAILED, "答题记录插入失败");
    }

    @Cacheable(key = "#user.id", condition = "#pageInfo.currentPage == 1")
    @Override
    public Page<QuestionVo> getQuestionRecord(PageInfo pageInfo, User user) {
        Page<AnswerRecord> recordPage = pageInfo.getPage();
        AbstractWrapper wrapper = this.query().eq(FiledConstant.USER_ID, user.getId())
                .orderByDesc(FiledConstant.CREATED_TIME).getWrapper();
        //查询答题记录
        this.page(recordPage, wrapper);
        List<AnswerRecord> records = recordPage.getRecords();
        //答题记录为空直接返回
        if (CollectionUtil.isEmpty(records)) {
            return null;
        }
        List<String> answerIds = new ArrayList<>();
        records.forEach(record -> {
            answerIds.add(record.getAnswerId());
        });
        //根据答案id查询问题
        List<Answer> answers = answerService.batchGetAnswer(answerIds);
        if (CollectionUtil.isEmpty(answers)) {
            throw new CRUDException(CRUDExceptionType.NOT_FOUND, "根据答案id查询不到对应答案");
        }
        //根据答案查询题目
        List<QuestionVo> questionVos = questionService.batchGetQuestion(answers);
        if (CollectionUtil.isEmpty(questionVos)) {
            throw new CRUDException(CRUDExceptionType.NOT_FOUND, "根据答案查询不到对应题目");
        }
        //将问题创建时间改成答题时间
        Map<String, String> qIdAnswerIdMap = new HashMap<>();
        answers.forEach(answer -> {
            qIdAnswerIdMap.put(answer.getQuestionId(), answer.getId());
        });
        HashMap<String, LocalDateTime> aIdCreatedTimeMap = new HashMap<>();
        records.forEach(record -> {
            aIdCreatedTimeMap.put(record.getAnswerId(), record.getCreatedTime());
        });
        questionVos.forEach(questionVo -> {
            String aId = qIdAnswerIdMap.get(questionVo.getId());
            LocalDateTime time = aIdCreatedTimeMap.get(aId);
            if (Objects.nonNull(aId) && Objects.nonNull(time)) {
                questionVo.setCreatedTime(time);
            } else {
                throw new BaseException("没有查询到对应的答题记录时间");
            }
        });
        //根据答题时间降序排序
        questionVos.sort((QuestionVo q1, QuestionVo q2) -> q2.getCreatedTime().compareTo(q1.getCreatedTime()));
        Page<QuestionVo> questionVoPage = pageInfo.getPage();
        questionVoPage.setRecords(questionVos);
        questionVoPage.setTotal(questionVos.size());
        return questionVoPage;
    }
}
