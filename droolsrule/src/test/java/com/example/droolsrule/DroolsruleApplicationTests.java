package com.example.droolsrule;

import com.example.droolsrule.model.Hello;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolsruleApplicationTests {

    @Test
    public void testRules() {
        KieServices kieServices = KieServices.get();
        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieClasspathContainer.newKieSession("ksession-rule");
        Hello hello = new Hello();
      //  hello.setUuid(UUID.randomUUID().toString());
        hello.setContent("hello world,first run drools!");
        kieSession.insert(hello);

        int count = kieSession.fireAllRules();
        System.out.println("命中了" + count + "条规则！");
        kieSession.dispose();
    }

}
