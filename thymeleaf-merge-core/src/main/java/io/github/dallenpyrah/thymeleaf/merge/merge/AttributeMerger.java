package io.github.dallenpyrah.thymeleaf.merge.merge;

import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;

import java.util.Map;

public interface AttributeMerger {
    IOpenElementTag merge(IModelFactory factory, IOpenElementTag open, Map<String, String> add);
}
