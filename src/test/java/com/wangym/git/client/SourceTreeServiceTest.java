package com.wangym.git.client;

import com.wangym.git.client.service.SourceTreeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SourceTreeServiceTest {

    @Autowired
    private SourceTreeService service;

    @Test
    public void test() throws IOException {
        service.handle();
    }

}
