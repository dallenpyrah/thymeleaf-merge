package io.github.dallenpyrah.thymeleaf.merge;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.AbstractElementModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.Map;

public final class ThMergeFragmentRootModelProcessor extends AbstractElementModelProcessor {
    public ThMergeFragmentRootModelProcessor(String prefix) {
        super(TemplateMode.HTML, prefix, null, false, "fragment", true, 40);
    }

    @Override
    protected void doProcess(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
        if (!AttributeUtils.hasPendingMerge(context)) return;
        Object o = context.getVariable(AttributeUtils.VAR_ATTRS);
        if (!(o instanceof Map)) return;
        Map<String, String> add = (Map<String, String>) o;
        if (model.size() == 0) return;
        ITemplateEvent e = model.get(0);
        if (!(e instanceof IOpenElementTag)) return;
        IModelFactory mf = context.getModelFactory();
        IOpenElementTag open = (IOpenElementTag) e;
        IOpenElementTag merged = AttributeUtils.mergeIntoOpenTag(mf, open, add);
        model.replace(0, merged);
        AttributeUtils.clearMergeOnce(structureHandler);
    }
}
