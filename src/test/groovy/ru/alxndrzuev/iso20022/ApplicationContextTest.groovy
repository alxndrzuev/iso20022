package ru.alxndrzuev.iso20022


import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [Application.class, TestConfig.class]
)
@ActiveProfiles("test")
class ApplicationContextTest {

    @Test
    void shouldPass() {
    }
}
