package ru.javawebinar.topjava.service;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
        "classpath:spring/spring-cacheOverride.xml"
)
public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
}
