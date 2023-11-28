package org.example.mvc;

import org.example.annotation.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;    // HandlerMapping 목록
    private List<ViewResolver> viewResolvers;        // ViewResolver 목록
    private List<HandlerAdapter> handlerAdapters;    // HandlerAdapter 목록

    @Override
    public void init() throws ServletException {
        // RequestMappingHandlerMapping과 AnnotationHandlerMapping을 초기화하여 목록에 추가
        RequestMappingHandlerMapping rmhm = new RequestMappingHandlerMapping();
        rmhm.init();

        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("org.example");
        ahm.initalize();

        handlerMappings = List.of(rmhm, ahm);

        // HandlerAdapter 목록에 SimpleControllerHandlerAdapter와 AnnotationHandlerAdapter 추가
        handlerAdapters = List.of(new SimpleControllerHandlerAdapter(), new AnnotationHandlerAdapter());

        // ViewResolver 목록에 JspViewResolver 추가
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("[DispatcherServlet] Service started.");
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        try {
            // 요청에 해당하는 Handler를 찾음
            Object handler = handlerMappings.stream()
                    .filter(hm -> hm.findHandler(new HandlerKey(requestMethod, requestURI)) != null)
                    .map(hm -> hm.findHandler(new HandlerKey(requestMethod, requestURI)))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No handler for[" + requestMethod + "," + requestURI + "]"));

            // Handler에 맞는 HandlerAdapter를 찾음
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler[" + handler + "]"));

            // HandlerAdapter를 통해 요청을 처리하고 ModelAndView를 받음
            ModelAndView mav = handlerAdapter.handle(request, response, handler);

            // ViewResolver를 통해 ModelAndView의 View 이름을 실제 View로 변환하여 렌더링
            for (ViewResolver viewResolver : viewResolvers) {
                View view = viewResolver.resolveView(mav.getViewName());
                view.rander(mav.getModel(), request, response);
            }

        } catch (Exception e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
