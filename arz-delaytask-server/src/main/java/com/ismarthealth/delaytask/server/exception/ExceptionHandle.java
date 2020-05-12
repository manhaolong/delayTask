package com.ismarthealth.delaytask.server.exception;

import com.ismarthealth.osp.core.common.Result;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 全局异常捕捉处理
 */
@ControllerAdvice
public class ExceptionHandle {

    private Logger errorLogger = LoggerFactory.getLogger("errorLog");

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e, HttpServletRequest request) {
        // 捕获手动抛出的异常
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            return new Result(baseException.getReturnCode(), baseException.getMessage());
        }
        e.printStackTrace();
        StringBuilder sb = new StringBuilder(512);
        sb.append("{\"LOG_TYPE\":\"EXCEPTION_HANDLER_LOG\",");
        sb.append("\"REQUEST_TIME\":\"").append(LocalDate.now()).append(" ").append(LocalTime.now()).append("\",");
        sb.append("\"URL\":\"").append(request.getRequestURL().toString()).append("\",");
        sb.append("\"PARAMS\":\"").append(request.getQueryString()).append("\",");
        sb.append("\"IP\":\"").append(request.getRemoteAddr()).append("\",");
        sb.append("\"TOKEN\":\"").append(request.getHeader("token")).append("\"}");
        errorLogger.error(sb.toString(), e);
        // TODO CODE码暂未定义
        return new Result(ReturnCodeEnum.CODE_4000000.getCode(), e.getMessage());
    }
}