package com.home;

import com.home.beans.Sum;
import com.home.beans.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionStoreIntegrationTest {

    @Test
    public void testSimplePutGet() {
        RestTemplate template = new RestTemplate();

        Transaction bean = new Transaction();

        bean.setAmount(5000d);
        bean.setType("cars");

        template.put("http://127.0.0.1:8080/transactionservice/transaction/10", bean);

        Transaction resultBean =
                template.getForObject ("http://127.0.0.1:8080/transactionservice/transaction/10", Transaction.class);

        Assert.assertEquals(resultBean.getAmount(), bean.getAmount());
    }

    @Test
    public void testExampleTypes() {
        RestTemplate template = new RestTemplate();

        createTestObjects();

        List<Integer> resultList =
                template.getForObject ("http://127.0.0.1:8080/transactionservice/types/cars", List.class);

        Assert.assertEquals( resultList.get(0).longValue(), 10L);
    }

    @Test
    public void testExampleSum1() {
        RestTemplate template = new RestTemplate();

        createTestObjects();

        Sum result =
                template.getForObject ("http://127.0.0.1:8080/transactionservice/sum/10", Sum.class);

        Assert.assertEquals(result.getSum().doubleValue(), 15000d, 0.1);
    }

    @Test
    public void testExampleSum2() {
        RestTemplate template = new RestTemplate();

        createTestObjects();

        Sum result =
                template.getForObject ("http://127.0.0.1:8080/transactionservice/sum/11", Sum.class);

        Assert.assertEquals(result.getSum().doubleValue(), 10000d, 0.1);
    }

    private void createTestObjects() {
        RestTemplate template = new RestTemplate();

        Transaction bean1 = new Transaction();

        bean1.setAmount(5000d);
        bean1.setType("cars");

        template.put("http://127.0.0.1:8080/transactionservice/transaction/10", bean1);

        Transaction bean2 = new Transaction();

        bean2.setAmount(10000d);
        bean2.setType("shopping");
        bean2.setParent_id(10L);

        template.put("http://127.0.0.1:8080/transactionservice/transaction/11", bean2);
    }
}
