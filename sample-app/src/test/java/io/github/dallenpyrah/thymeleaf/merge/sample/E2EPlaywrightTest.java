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

    @Test
    public void idOverrideAppliedOnWrapper() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/id");
        Locator root = page.locator("body > div").first();
        assertEquals("hid", root.getAttribute("id"));
        ctx.close();
    }

    @Test
    public void excludesThAttributesAndDoesNotLeak() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/exclude");
        Locator root = page.locator("body > div").first();
        assertEquals("v", root.getAttribute("data-k"));
        assertEquals(null, root.getAttribute("data-x"));
        assertEquals(null, root.getAttribute("th:id"));
        assertEquals(null, root.getAttribute("th:class"));
        assertEquals(null, root.getAttribute("th:attr"));
        ctx.close();
    }

    @Test
    public void assortedAttributesMergedAndVisible() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/attrs");
        Locator root = page.locator("body > div").first();
        assertEquals("/host-get", root.getAttribute("hx-get"));
        assertEquals("/host-post", root.getAttribute("hx-post"));
        assertEquals("#tgt", root.getAttribute("hx-target"));
        assertEquals("click", root.getAttribute("hx-trigger"));
        assertEquals("outerHTML", root.getAttribute("hx-swap"));
        assertNotNull(root.getAttribute("hx-headers"));
        assertNotNull(root.getAttribute("hx-vals"));
        assertEquals("A", root.getAttribute("data-a"));
        assertEquals("B", root.getAttribute("data-b"));
        assertEquals("label", root.getAttribute("aria-label"));
        assertEquals("true", root.getAttribute("aria-checked"));
        String style = root.getAttribute("style");
        assertTrue(style.contains("color:red"));
        assertTrue(style.contains("padding:3px"));
        ctx.close();
    }

    @Test
    public void manyHxAttributesPresent() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/hxheavy");
        Locator root = page.locator("body > div").first();
        assertEquals("/get", root.getAttribute("hx-get"));
        assertEquals("/post", root.getAttribute("hx-post"));
        assertEquals("/put", root.getAttribute("hx-put"));
        assertEquals("/delete", root.getAttribute("hx-delete"));
        assertEquals("/patch", root.getAttribute("hx-patch"));
        assertEquals("true", root.getAttribute("hx-boost"));
        assertEquals("true", root.getAttribute("hx-push-url"));
        assertEquals(".sel", root.getAttribute("hx-select"));
        assertEquals("#oob", root.getAttribute("hx-select-oob"));
        assertEquals("Are you sure?", root.getAttribute("hx-confirm"));
        assertEquals("#btn", root.getAttribute("hx-disabled-elt"));
        assertEquals("*", root.getAttribute("hx-disinherit"));
        assertEquals("multipart/form-data", root.getAttribute("hx-encoding"));
        assertEquals("morphdom-swap", root.getAttribute("hx-ext"));
        assertEquals("#form", root.getAttribute("hx-include"));
        assertEquals("#spinner", root.getAttribute("hx-indicator"));
        assertNotNull(root.getAttribute("hx-on"));
        assertEquals("Enter", root.getAttribute("hx-prompt"));
        assertEquals("a,b", root.getAttribute("hx-params"));
        assertEquals("true", root.getAttribute("hx-preserve"));
        assertEquals("true", root.getAttribute("hx-replace-url"));
        assertNotNull(root.getAttribute("hx-request"));
        assertEquals("this:queue", root.getAttribute("hx-sync"));
        assertEquals("#target", root.getAttribute("hx-target"));
        assertEquals("change", root.getAttribute("hx-trigger"));
        assertEquals("innerHTML", root.getAttribute("hx-swap"));
        assertEquals("true", root.getAttribute("hx-swap-oob"));
        assertNotNull(root.getAttribute("hx-vals"));
        ctx.close();
    }

    @Test
    public void manyDataAttributesPresent() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/dataheavy");
        Locator root = page.locator("body > div").first();
        assertEquals("123", root.getAttribute("data-id"));
        assertEquals("u-456", root.getAttribute("data-user-id"));
        assertEquals("yes", root.getAttribute("data-test"));
        assertEquals("baz", root.getAttribute("data-foo-bar"));
        assertNotNull(root.getAttribute("data-json"));
        assertNotNull(root.getAttribute("data-list"));
        ctx.close();
    }

    @Test
    public void manyAriaAndStdAttributesPresent() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/ariaheavy");
        Locator root = page.locator("body > div").first();
        assertEquals("Label", root.getAttribute("aria-label"));
        assertEquals("lbl", root.getAttribute("aria-labelledby"));
        assertEquals("desc", root.getAttribute("aria-describedby"));
        assertEquals("false", root.getAttribute("aria-expanded"));
        assertEquals("true", root.getAttribute("aria-hidden"));
        assertEquals("true", root.getAttribute("aria-pressed"));
        assertEquals("button", root.getAttribute("role"));
        assertEquals("2", root.getAttribute("tabindex"));
        assertEquals("true", root.getAttribute("draggable"));
        assertEquals("true", root.getAttribute("contenteditable"));
        assertEquals("false", root.getAttribute("spellcheck"));
        ctx.close();
    }

    @Test
    public void stdAttributesPresent() {
        BrowserContext ctx = browser.newContext();
        Page page = ctx.newPage();
        page.navigate("http://localhost:" + port + "/stdattrs");
        Locator root = page.locator("body > div").first();
        assertEquals("std", root.getAttribute("id"));
        assertEquals("Title", root.getAttribute("title"));
        assertEquals("en", root.getAttribute("lang"));
        assertEquals("ltr", root.getAttribute("dir"));
        String cls = root.getAttribute("class");
        assertTrue(cls.contains("alpha"));
        assertTrue(cls.contains("beta"));
        assertTrue(root.getAttribute("style").contains("margin:1px"));
        ctx.close();
    }
}
