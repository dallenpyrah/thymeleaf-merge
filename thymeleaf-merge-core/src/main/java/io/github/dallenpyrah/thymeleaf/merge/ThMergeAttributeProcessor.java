package io.github.dallenpyrah.thymeleaf.merge;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Map;

public final class ThMergeAttributeProcessor extends AbstractAttributeTagProcessor {
    public ThMergeAttributeProcessor(String prefix) {
        super(TemplateMode.HTML, prefix, null, false, "merge", true, 50, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
        Map<String, String> attrs = AttributeUtils.extractHostMergeableAttributes(tag);
        AttributeUtils.storeMergeState(structureHandler, attrs);
        IModelFactory mf = context.getModelFactory();
        org.thymeleaf.model.IModel m = mf.createModel();
        java.util.Map<String,String> a = new java.util.LinkedHashMap<>();
        a.put("th:replace", attributeValue);
        org.thymeleaf.model.IOpenElementTag o = mf.createOpenElementTag(tag.getElementCompleteName(), a, org.thymeleaf.model.AttributeValueQuotes.DOUBLE, false);
        m.add(o);
        m.add(mf.createCloseElementTag(tag.getElementCompleteName()));
        structureHandler.replaceWith(m, true);
    }
}
