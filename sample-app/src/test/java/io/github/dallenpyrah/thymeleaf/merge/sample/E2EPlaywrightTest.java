package io.github.dallenpyrah.thymeleaf.merge.sample;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2EPlaywrightTest {
    @LocalServerPort
    private int port;

    private static Playwright pw;
    private static Browser browser;

    @BeforeAll
    public static void beforeAll() {
        pw = Playwright.create();
        browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    public static void afterAll() {
        if (browser != null) {
            browser.close();
        }
        if (pw != null) {
            pw.close();
        }
    }

    @Test
    public void pageRendersAndAttributesMerged() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/");
        Locator root = page.locator("body > div").first();
        String hx = root.getAttribute("hx-get");
        assertEquals("/items", hx);
        String cls = root.getAttribute("class");
        assertTrue(cls.contains("host"));
        assertNotNull(page.locator("section.frag").first());
        ctx.close();
    }
}
