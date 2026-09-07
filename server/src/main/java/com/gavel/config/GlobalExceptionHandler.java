package com.gavel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.gavel.entity.EIException;
import com.gavel.utils.R;

/**
 * 全局异常处理：统一返回 R 结构，避免向前端泄漏堆栈信息。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** 业务异常：返回具体提示 */
    @ExceptionHandler(EIException.class)
    public R handleEI(EIException e) {
        return R.error(e.getCode(), e.getMsg());
    }

    /** 唯一键冲突 */
    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKey(DuplicateKeyException e) {
        log.warn("唯一键冲突: {}", e.getMessage());
        return R.error("数据已存在，请勿重复提交");
    }

    /** 缺少必要参数 */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleMissingParam(MissingServletRequestParameterException e) {
        return R.error("缺少必要参数：" + e.getParameterName());
    }

    /** 上传大小超限 */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R handleMaxUpload(MaxUploadSizeExceededException e) {
        return R.error("上传文件过大，最大允许 10MB");
    }

    /** 静态资源不存在 */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R handleNotFound(NoResourceFoundException e) {
        return R.error(404, "资源不存在");
    }

    /** 兜底：只记日志，不向外暴露内部细节 */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleException(Exception e) {
        log.error("未处理异常", e);
        return R.error("系统繁忙，请稍后重试");
    }
}
