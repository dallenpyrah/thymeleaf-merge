package io.github.dallenpyrah.thymeleaf.merge.merge;

import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class SmartAttributeMerger implements AttributeMerger {
    @Override
    public IOpenElementTag merge(IModelFactory mf, IOpenElementTag open, Map<String, String> add) {
        Map<String, String> base = new LinkedHashMap<>(open.getAttributeMap());
        String baseClass = base.get("class");
        String addClass = add.get("class");
        if (addClass != null || baseClass != null) {
            Set<String> set = new LinkedHashSet<>();
            if (baseClass != null && !baseClass.isEmpty()) {
                for (String c : baseClass.trim().split("\\s+")) if (!c.isEmpty()) set.add(c);
            }
            if (addClass != null && !addClass.isEmpty()) {
                for (String c : addClass.trim().split("\\s+")) if (!c.isEmpty()) set.add(c);
            }
            StringBuilder sb = new StringBuilder();
            for (String c : set) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(c);
            }
            if (sb.length() > 0) base.put("class", sb.toString());
        }
        String baseStyle = base.get("style");
        String addStyle = add.get("style");
        if (baseStyle != null && addStyle != null) {
            String s = baseStyle.trim();
            if (!s.endsWith(";")) s = s + ";";
            base.put("style", s + addStyle.trim());
        } else if (addStyle != null && baseStyle == null) {
            base.put("style", addStyle);
        }
        for (Map.Entry<String, String> e : add.entrySet()) {
            String k = e.getKey();
            if ("class".equalsIgnoreCase(k) || "style".equalsIgnoreCase(k)) continue;
            base.put(k, e.getValue());
        }
        return mf.createOpenElementTag(open.getElementCompleteName(), base, AttributeValueQuotes.DOUBLE, false);
    }
}
