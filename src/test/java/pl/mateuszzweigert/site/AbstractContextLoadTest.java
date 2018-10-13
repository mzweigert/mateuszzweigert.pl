package pl.mateuszzweigert.site;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class AbstractContextLoadTest<T> {

    @Autowired
    private T controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
