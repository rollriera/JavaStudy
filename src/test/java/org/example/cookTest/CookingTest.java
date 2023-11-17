package org.example.cookTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CookingTest {

    @DisplayName("메뉴에 해당하는 음식을 만든다.")
    @Test
    void makeCookTest() {

        // 요리사 객체 생성
        Cooking cooking = new Cooking();

        // 요리사에게 전달할 메뉴 항목 생성
        MenuItem menuItem = new MenuItem("돈까스",5000);

        // 요리사에게 해당 메뉴를 만들어달라고 요청
        Cook cook = cooking.makeCook(menuItem);

        // 그요리가 해당 메뉴가 메뉴에 항목과 일치하는지 검사
        assertThat(cook).isEqualTo(new Cook("돈까스",5000));
    }
}
