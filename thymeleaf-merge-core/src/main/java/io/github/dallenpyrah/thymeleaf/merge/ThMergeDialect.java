package io.github.dallenpyrah.thymeleaf.merge;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

public final class ThMergeDialect extends AbstractProcessorDialect {
    public ThMergeDialect() {
        super("Thymeleaf Merge Dialect", "th", StandardDialect.PROCESSOR_PRECEDENCE - 10);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> s = new HashSet<>();
        s.add(new ThMergeAttributeProcessor(dialectPrefix));
        s.add(new ThMergeFragmentRootModelProcessor(dialectPrefix));
        return s;
    }
}
