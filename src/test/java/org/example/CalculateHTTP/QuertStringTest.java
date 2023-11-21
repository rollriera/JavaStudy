package org.example.CalculateHTTP;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuertStringTest {

    // operand1=11
    @Test
    void createTest() {
        QueryString queryString = new QueryString("operand1","11");

        assertThat(queryString).isNotNull();
    }
}
