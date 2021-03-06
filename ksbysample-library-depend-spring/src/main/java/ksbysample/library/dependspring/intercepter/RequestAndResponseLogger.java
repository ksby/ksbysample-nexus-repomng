package ksbysample.library.dependspring.intercepter;

import com.google.common.collect.Iterators;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.StreamSupport;

public class RequestAndResponseLogger implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String LOG_REQUEST_INFO = "[req][info  ] ";
    private static final String LOG_REQUEST_HEADER = "[req][header] ";
    private static final String LOG_REQUEST_COOKIE = "[req][cookie] ";
    private static final String LOG_REQUEST_PARAMETER = "[req][param ] ";

    private static final String LOG_RESPONSE_INFO = "[res][info  ] ";
    private static final String LOG_RESPONSE_HEADER = "[res][header] ";

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        loggingRequest(request);
        Object ret = invocation.proceed();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        loggingResponse(response);

        return ret;
    }

    private void loggingRequest(HttpServletRequest request) {
        loggingRequestInfo(request);
        loggingRequestHeaders(request);
        loggingRequestCookies(request);
        loggingRequestParameters(request);
    }

    private void loggingResponse(HttpServletResponse response) {
        loggingResponseInfo(response);
        loggingResponseHeaders(response);
    }

    private void loggingRequestInfo(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        append(sb, "REQUEST_URI", request.getRequestURI());
        append(sb, ", SERVLET_PATH", request.getServletPath());
        logging(LOG_REQUEST_INFO, null, sb.toString());

        sb = new StringBuilder();
        append(sb, "CHARACTER_ENCODING", request.getCharacterEncoding());
        append(sb, ", CONTENT_LENGTH", String.valueOf(request.getContentLength()));
        append(sb, ", CONTENT_TYPE", request.getContentType());
        append(sb, ", LOCALE", String.valueOf(request.getLocale()));
        append(sb, ", SCHEME", request.getScheme());
        logging(LOG_REQUEST_INFO, null, sb.toString());

        sb = new StringBuilder();
        append(sb, "REMOTE_ADDR", request.getRemoteAddr());
        append(sb, ", REMOTE_HOST", request.getRemoteHost());
        append(sb, ", SERVER_NAME", request.getServerName());
        append(sb, ", SERVER_PORT", String.valueOf(request.getServerPort()));
        logging(LOG_REQUEST_INFO, null, sb.toString());

        sb = new StringBuilder();
        append(sb, "CONTEXT_PATH", request.getContextPath());
        append(sb, ", REQUEST_METHOD", request.getMethod());
        append(sb, ", QUERY_STRING", request.getQueryString());
        append(sb, ", PATH_INFO", request.getPathInfo());
        append(sb, ", REMOTE_USER", request.getRemoteUser());
        logging(LOG_REQUEST_INFO, null, sb.toString());
    }

    private void loggingRequestHeaders(HttpServletRequest request) {
        Iterable<String> headerNameList = () -> Iterators.forEnumeration(request.getHeaderNames());
        StreamSupport.stream(headerNameList.spliterator(), false)
                .filter(headerName -> !StringUtils.equals(headerName, "cookie"))
                .forEach(headerName -> {
                    Iterable<String> headerList = () -> Iterators.forEnumeration(request.getHeaders(headerName));
                    headerList.forEach(header -> logging(LOG_REQUEST_HEADER, headerName, header));
                });
    }

    private void loggingRequestCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            Arrays.asList(request.getCookies()).forEach(cookie -> {
                StringBuilder sb = new StringBuilder();
                sb.append("name = ");
                sb.append(cookie.getName());
                sb.append(", value = ");
                sb.append(cookie.getValue());
                sb.append(", domain = ");
                sb.append(cookie.getDomain());
                sb.append(", path = ");
                sb.append(cookie.getPath());
                sb.append(", maxage = ");
                sb.append(cookie.getMaxAge());
                sb.append(", secure = ");
                sb.append(cookie.getSecure());
                sb.append(", httponly = ");
                sb.append(cookie.isHttpOnly());
                logging(LOG_REQUEST_COOKIE, null, sb.toString());
            });
        }
    }

    private void loggingRequestParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, values) -> {
            Arrays.asList(values).forEach(value -> logging(LOG_REQUEST_PARAMETER, key, value));
        });
    }

    private void loggingResponseInfo(HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        append(sb, "RESPONSE_STATUS", String.valueOf(response.getStatus()));
        append(sb, ", CHARACTER_ENCODING", response.getCharacterEncoding());
        append(sb, ", CONTENT_TYPE", response.getContentType());
        append(sb, ", LOCALE", String.valueOf(response.getLocale()));
        logging(LOG_RESPONSE_INFO, null, sb.toString());
    }

    private void loggingResponseHeaders(HttpServletResponse response) {
        response.getHeaderNames().forEach(headerName -> {
            response.getHeaders(headerName).forEach(header -> logging(LOG_RESPONSE_HEADER, headerName, header));
        });
    }

    private void logging(String title, String name, String value) {
        StringBuilder sb = new StringBuilder(title);
        if (name != null) {
            sb.append(name);
            sb.append(" = ");
        }
        sb.append(value);
        logger.info(sb.toString());
    }

    private void append(StringBuilder sb, String name, String value) {
        sb.append(name);
        sb.append(" = ");
        sb.append(value);
    }

}
