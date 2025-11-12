package io.github.dallenpyrah.thymeleaf.merge;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;

public final class ThMergeEngineConfigurer {
    public static void register(TemplateEngine engine) {
        engine.addDialect(new ThMergeDialect());
    }
}
