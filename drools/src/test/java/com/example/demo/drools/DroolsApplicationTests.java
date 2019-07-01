package com.example.demo.drools;

import com.example.demo.drools.model.Hello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolsApplicationTests {

    @Test
    public void testRules() {
        KieServices kieServices = KieServices.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession("ksession-rule");
        Hello hello = new Hello();
        hello.setUuid(UUID.randomUUID().toString());
        hello.setContent("hello world,first run drools!");
        kieSession.insert(hello);

        int count = kieSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        kieSession.dispose();
    }

}
