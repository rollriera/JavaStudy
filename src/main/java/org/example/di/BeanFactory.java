package org.example.di;

import org.example.Controller.UserController;
import org.example.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanFactory {

    private final Set<Class<?>> preInstantiatedClazz;

    // instance가 필요하여 class 타입 객체를 키로 instance를 value로 가지게 되는 변수 선언
    private Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {

        // 전달받은 class 타입의 객체를 초기화
        this.preInstantiatedClazz = preInstantiatedClazz;
        initialize();
    }

    private void initialize() {

        // class 타입 객체를 가지고 instance를 생성하여 초기화 시켜준다.
        for (Class<?> clazz : preInstantiatedClazz) {
            Object instance = createInstance(clazz);
            beans.put(clazz, instance);
        }
    }

    private Object createInstance(Class<?> clazz) {

        // 생성자
        // class타입 객체로 생성자를 조회
        Constructor<?> constructor = findConstructor(clazz);

        // 파라미터
        // 생성자의 파라미터 정보를 조회
        List<Object> parameters = new ArrayList<>();
        for(Class<?> typeClass : constructor.getParameterTypes()) {
            parameters.add(getParameterByClass(typeClass));
        }
        // 인스턴스 생성
        // 위 정보들을 이용해 instance를 생성
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // 생성자 조회 메서드
    private Constructor<?> findConstructor(Class<?> clazz) {

        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        // 그것이 있다면 return 없다면 첫번째 요소를 반환
        if(Objects.nonNull(constructor)){
            return constructor;
        }
        return clazz.getDeclaredConstructors()[0];
    }

    private Object getParameterByClass(Class<?> typeClass) {

        // getBean을 통해 class 타입 객체를 키로 가지는 instance가 있는지를 검사
        Object instanceBean = getBean(typeClass);

        // 만약 instance가 있으면 바로 return 없다면 해당 부분을 재기 호출을 통해 필요 대상부터 instance를 생성해준다.
        if(Objects.nonNull(instanceBean)){
            return instanceBean;
        }
        return createInstance(typeClass);
    }

    public <T> T getBean(Class<?> requiredType) {
        return (T) beans.get(requiredType);
    }
}
