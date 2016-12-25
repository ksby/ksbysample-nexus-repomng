package ksbysample.library.dependspring.config;

import ksbysample.library.dependspring.Application;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(Enclosed.class)
public class RequestAndResponseLoggerAutoConfigurationTest {

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(classes = Application.class)
    @WebAppConfiguration
    public static class デフォルトの設定でのテスト {

        public MockMvc mvc;

        @Autowired
        private WebApplicationContext context;

        @Rule
        public OutputCapture capture = new OutputCapture();

        @Before
        public void setUp() throws Exception {
            this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
                    .build();
        }

        @Test
        public void ログが出力される() throws Exception {
            mvc.perform(get("/test"))
                    .andExpect(status().isOk());
            assertThat(capture.toString()).contains("[req][info  ] REQUEST_URI = /test");
            assertThat(capture.toString()).contains("[res][info  ] RESPONSE_STATUS = 200");
        }

    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(classes = Application.class)
    @WebAppConfiguration
    @TestPropertySource(properties = {"ksbysample.library.request-and-response-logger.execution=execution(* ksbysample.library.dependspring..*.*(..))"})
    public static class executionプロパティ設定時のテスト_出力される設定 {

        public MockMvc mvc;

        @Autowired
        private WebApplicationContext context;

        @Rule
        public OutputCapture capture = new OutputCapture();

        @Before
        public void setUp() throws Exception {
            this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
                    .build();
        }

        @Test
        public void ログが出力される() throws Exception {
            mvc.perform(get("/test"))
                    .andExpect(status().isOk());
            assertThat(capture.toString()).contains("[req][info  ] REQUEST_URI = /test");
            assertThat(capture.toString()).contains("[res][info  ] RESPONSE_STATUS = 200");
        }

    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(classes = Application.class)
    @WebAppConfiguration
    @TestPropertySource(properties = {"ksbysample.library.request-and-response-logger.execution=execution(* ksbysample.webapp.lending.web..*.*(..))"})
    public static class executionプロパティ設定時のテスト_出力されない設定 {

        public MockMvc mvc;

        @Autowired
        private WebApplicationContext context;

        @Rule
        public OutputCapture capture = new OutputCapture();

        @Before
        public void setUp() throws Exception {
            this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
                    .build();
        }

        @Test
        public void ログが出力されない() throws Exception {
            mvc.perform(get("/test"))
                    .andExpect(status().isOk());
            assertThat(capture.toString()).doesNotContain("[req][info  ] REQUEST_URI = /test");
            assertThat(capture.toString()).doesNotContain("[res][info  ] RESPONSE_STATUS = 200");
        }

    }

}
