package io.github.dallenpyrah.thymeleaf.merge;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import io.github.dallenpyrah.thymeleaf.merge.merge.AttributeMerger;
import io.github.dallenpyrah.thymeleaf.merge.merge.SmartAttributeMerger;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class AttributeUtils {
    public static final String VAR_ATTRS = "__thmerge_attrs";
    public static final String VAR_FLAG = "__thmerge_once";

    public static Map<String, String> extractHostMergeableAttributes(IProcessableElementTag tag) {
        Map<String, String> map = new LinkedHashMap<>();
        for (String name : tag.getAttributeMap().keySet()) {
            String v = tag.getAttributeValue(name);
            if (v == null) continue;
            String n = name;
            if (n == null) continue;
            if (n.contains(":")) continue;
            if (n.equalsIgnoreCase("xmlns")) continue;
            if (n.toLowerCase(Locale.ROOT).startsWith("xmlns")) continue;
            if (n.equalsIgnoreCase("th:merge")) continue;
            if (n.toLowerCase(Locale.ROOT).startsWith("th:")) continue;
            map.put(n, v);
        }
        return map;
    }

    public static void storeMergeState(IElementTagStructureHandler sh, Map<String, String> attrs) {
        sh.setLocalVariable(VAR_ATTRS, attrs);
        sh.setLocalVariable(VAR_FLAG, Boolean.TRUE);
    }

    public static boolean hasPendingMerge(ITemplateContext ctx) {
        Object f = ctx.getVariable(VAR_FLAG);
        return Boolean.TRUE.equals(f);
    }

    public static void clearMergeOnce(IElementTagStructureHandler sh) { sh.removeLocalVariable(VAR_FLAG); }
    public static void clearMergeOnce(IElementModelStructureHandler sh) { sh.removeLocalVariable(VAR_FLAG); }

    public static IOpenElementTag mergeIntoOpenTag(IModelFactory mf, IOpenElementTag open, Map<String, String> add) {
        AttributeMerger merger = new SmartAttributeMerger();
        return merger.merge(mf, open, add);
    }
}
