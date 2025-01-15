package com.litao.activiti;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProcessSaveTest {

    @ParameterizedTest
    @ValueSource(strings = {"C:\\Users\\Acer\\Desktop\\bpmn-01.bpmn.xml"})
    public void testSaveFromFile(String file){

    }
}
