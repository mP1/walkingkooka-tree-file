[![Build Status](https://travis-ci.com/mP1/walkingkooka-tree-file.svg?branch=master)](https://travis-ci.com/mP1/walkingkooka-tree-file.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-tree-file/badge.svg?branch=master)](https://coveralls.io/github/mP1/walkingkooka-tree-file?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-tree-file.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-tree-file/context:java)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-tree-file.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-tree-file/alerts/)



A [walkingkooka/tree.Node](https://github.com/mP1/walkingkooka/blob/master/Node.md) read only view of a file system files and directories, primarily interesting because of xpath selection [walkingkooka/tree.NodeSelector](https://github.com/mP1/walkingkooka/blob/master/src/main/java/walkingkooka/tree/select/NodeSelector.java)

The snipper below is taken from [ReadmeSample.java](https://github.com/mP1/walkingkooka-tree-file/tree/master/src/test/java/walkingkooka/tree/file/ReadmeSample.java).

```java
    public static void main(final String[] args) throws Exception {
        System.out.println(Arrays.stream(args).collect(Collectors.joining(" ", "Command line args:\n", "\n")));

        final Path baseDir = Paths.get(args[0]);
        final String selector = args[1];
        final String containsText = args[2];

        // node selector parser
        final Parser<NodeSelectorParserContext> parser = NodeSelectorParsers.expression()
                .orReport(ParserReporters.basic())
                .cast();

        // parse into a NodeSelector 
        final NodeSelector<FilesystemNode, FilesystemNodeName, FilesystemNodeAttributeName, String> find = FilesystemNode.nodeSelectorExpressionParserToken(
                parser.parse(TextCursors.charSequence(selector), NodeSelectorParserContexts.basic(DecimalNumberContexts.american(MathContext.DECIMAL32))))
                        .map(NodeSelectorExpressionParserToken.class::cast)
                        .orElseThrow(() -> new Exception("Failed to parse selector")),
                Predicates.always());

        final FilesystemNodeContext filesystemNodeContext = FilesystemNodeContexts.basic(baseDir);

        // stream, filter if files contain arg[2] and then print matching files.
        find.stream(filesystemNodeContext.directory(baseDir),
                NodeSelectorContexts.basicFunctions(),
                Converters.simple(), // many functions operate on strings converters convert values to strings.
                ConverterContexts.basic(DateTimeContexts.fake(), DecimalNumberContexts.american(MathContext.DECIMAL32)), // used when parsing numbers within expressions.
                FilesystemNode.class)
                .filter(f -> {
                    // filter equivalent of [contains(@text, "insert arg2 here"])
                    try {
                        return filesystemNodeContext.text(f.path).contains(containsText);
                    } catch (final Exception cause) {
                        return false;
                    }
                })
                .forEach(System.out::println);
    }
```

Using the following command line.
```
/Users/miroslav/repos-github/walkingkooka-tree-file //*[ends-with(name(node()),".java")] Sample
```

The following output is produced.
```text
/Users/miroslav/repos-github/walkingkooka-tree-file/src/test/java/walkingkooka/tree/file/ReadmeSample.java
```

The steps in performed using the given command line arguments, may be summarised into the following:

- Starting at path base (argument #1)
- Parse (argument #2) into a `NodeSelector`.
- Create a `Stream`, which honours the parsed selector.
- Selector filters only files ending with `.java`.
- Filter only matches files that contain (argument #3).
