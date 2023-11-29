package org.example.di;

import org.example.Controller.UserController;
import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class BeanFactoryTest {
    private Reflections reflections;

    private BeanFactory beanFactory;

    // 테스트 메서드가 호출되기 전에 미리 호출되어 실행되는 메서드
    @BeforeEach
    void setUp() {

        // BasePackage가 org.example 밑에 있는 class가 Reflection 기술에 대한 대상.
        reflections = new Reflections("org.example");

        // UserController, UserService
        // 컨트롤러 서비스 어노테이션이 붙어져 있는 class 타입의 객체를 모두 조회해 옴
        Set<Class<?>> preInstantiatedClazz = getTypesAnnotatedWith(Controller.class, Service.class);

        // 조회된 class 타입의 객체를 beanFactory 생성자에 전달.
        beanFactory = new BeanFactory(preInstantiatedClazz);
    }

    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = new HashSet<>();
        for (Class<? extends Annotation> annotation: annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }

    @Test
    void diTest() {
        UserController userController = beanFactory.getBean(UserController.class);

        assertThat(userController).isNotNull();
        assertThat(userController.getUserService()).isNotNull();
    }
}