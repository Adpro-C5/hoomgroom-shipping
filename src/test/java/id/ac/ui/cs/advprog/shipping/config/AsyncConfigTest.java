package id.ac.ui.cs.advprog.shipping.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AsyncConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void testAsyncTaskExecutorBean() {
        ThreadPoolTaskExecutor asyncTaskExecutor = (ThreadPoolTaskExecutor) context.getBean("asyncTaskExecutor");
        assertNotNull(asyncTaskExecutor);

        assertEquals(4, asyncTaskExecutor.getCorePoolSize());
        assertEquals(100, asyncTaskExecutor.getQueueCapacity());
        assertEquals(4, asyncTaskExecutor.getMaxPoolSize());
        assertEquals("AsyncThread-", asyncTaskExecutor.getThreadNamePrefix());
    }
}
