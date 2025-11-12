package io.github.dallenpyrah.thymeleaf.merge;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThMergeProcessorTest {
    private TemplateEngine engine() {
        ClassLoaderTemplateResolver r = new ClassLoaderTemplateResolver();
        r.setPrefix("templates/");
        r.setSuffix(".html");
        r.setTemplateMode(TemplateMode.HTML);
        r.setCacheable(false);
        TemplateEngine e = new TemplateEngine();
        e.setTemplateResolver(r);
        ThMergeEngineConfigurer.register(e);
        return e;
    }

    @Test
    public void basicMerge() {
        TemplateEngine e = engine();
        String out = e.process("basic", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("div:has(> section.frag)");
        assertNotNull(root);
        assertEquals("/items", root.attr("hx-get"));
        assertEquals("1", root.attr("data-foo"));
        String rcls = root.className();
        assertTrue(rcls.contains("host"));
        assertTrue(rcls.contains("a"));
        assertTrue(rcls.contains("b"));
        assertTrue(root.attr("style").contains("color:red"));
        Element sec = root.selectFirst("section.frag");
        assertTrue(sec.attr("style").contains("background:blue"));
    }

    @Test
    public void overrideId() {
        TemplateEngine e = engine();
        String out = e.process("overrideId", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("div:has(> section.frag)");
        assertNotNull(root);
        assertEquals("hid", root.id());
    }

    @Test
    public void multipleMergesIndependent() {
        TemplateEngine e = engine();
        String out = e.process("multi", new Context());
        Document d = Jsoup.parse(out);
        Element r1 = d.select("body > div").get(0);
        Element r2 = d.select("body > div").get(1);
        assertTrue(r1.classNames().contains("one"));
        assertTrue(r2.classNames().contains("two"));
        assertEquals("/a", r1.attr("hx-post"));
        assertEquals("x", r2.attr("data-x"));
    }

    @Test
    public void excludesThAttributes() {
        TemplateEngine e = engine();
        String out = e.process("excludeThAttrs", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("body > div");
        assertNotNull(root);
        assertEquals("v", root.attr("data-k"));
        assertEquals("", root.attr("data-x"));
        assertEquals("", root.attr("th:id"));
        assertEquals("", root.attr("th:class"));
        assertEquals("", root.attr("th:attr"));
    }

    @Test
    public void styleMergeAndLastWins() {
        TemplateEngine e = engine();
        String out = e.process("styleMerge", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("body > div");
        assertNotNull(root);
        String style = root.attr("style");
        assertTrue(style.contains("color:red"));
        assertTrue(style.contains("padding:3px"));
        Element sec = root.selectFirst("section.frag");
        assertTrue(sec.attr("style").contains("background:blue"));
    }

    @Test
    public void hostOverridesNormalAttributes() {
        TemplateEngine e = engine();
        String out = e.process("hostData", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("body > div");
        assertNotNull(root);
        assertEquals("host", root.attr("data-foo"));
    }

    @Test
    public void hostOverridesFragmentHxAttr() {
        TemplateEngine e = engine();
        String out = e.process("hostHx", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("body > div");
        assertNotNull(root);
        assertEquals("/host", root.attr("hx-get"));
    }

    @Test
    public void thMergeAttributeIsNotInOutput() {
        TemplateEngine e = engine();
        String out = e.process("basic", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("body > div");
        assertNotNull(root);
        assertEquals("", root.attr("th:merge"));
    }

    @Test
    public void wrapperHasHostClasses() {
        TemplateEngine e = engine();
        String out = e.process("basicFragClassHost", new Context());
        Document d = Jsoup.parse(out);
        Element root = d.selectFirst("body > div");
        assertNotNull(root);
        String classes = root.className();
        assertTrue(classes.contains("frag"));
        assertTrue(classes.contains("host"));
        assertTrue(classes.contains("a"));
    }
}
