# Thymeleaf Merge

`th:merge` lets you include a fragment and merge all non-`th:*` attributes from the host element onto the fragment root. This removes the need to add parameters for every attribute like `hx-*`, `data-*`, `aria-*`, etc.

## Install

Maven (core):

```xml
<dependency>
  <groupId>io.github.dallenpyrah</groupId>
  <artifactId>thymeleaf-merge-core</artifactId>
  <version>0.1.0-SNAPSHOT</version>
  </dependency>
```

Spring Boot starter (auto-registers `th:merge`):

```xml
<dependency>
  <groupId>io.github.dallenpyrah</groupId>
  <artifactId>thymeleaf-merge-spring-boot-starter</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

Non-Boot setup:

```java
TemplateEngine engine = new TemplateEngine();
ThMergeEngineConfigurer.register(engine);
```

## Usage

```html
<div th:merge="~{fragments :: card}" hx-get="/items" hx-post="/submit" class="host"></div>
```

- The fragment is processed like `th:replace`.
- Host attributes merged onto the fragment root:
  - class: union merge (fragment first, then host), de-duplicated
  - style: concatenated with `;` (fragment first, host last-wins for duplicates)
  - others: host overrides fragment
- Excluded from merge: `th:*`, `xmlns:*`, `th:merge`.

## Examples

```html
<!-- fragments.html -->
<div th:fragment="card">
  <section class="frag" id="f1" style="background:blue"></section>
</div>

<!-- page.html -->
<div th:merge="~{fragments :: card}" class="host a b" style="color:red" hx-get="/items" data-foo="1"></div>
```

Result root element:

```html
<section class="frag host a b" id="f1" style="background:blue;color:red" hx-get="/items" data-foo="1"></section>
```

## License

Apache-2.0
# thymeleaf-merge
