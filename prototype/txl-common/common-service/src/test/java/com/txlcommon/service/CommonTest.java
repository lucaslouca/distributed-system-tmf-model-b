package com.txlcommon.service;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommonTest {

    private ApplicationContext applicationContext;


    @Before
    public void setUp() {
        applicationContext = new ClassPathXmlApplicationContext("test-application-context.xml");
    }

}
