[![Build Status](https://github.com/mP1/walkingkooka-tree-file/actions/workflows/build.yaml/badge.svg)](https://github.com/mP1/walkingkooka-tree-file/actions/workflows/build.yaml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/mP1/walkingkooka-tree-file/badge.svg)](https://coveralls.io/github/mP1/walkingkooka-tree-file)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/mP1/walkingkooka-tree-file.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-tree-file/context:java)
![](https://tokei.rs/b1/github/mP1/walkingkooka-tree-file)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/mP1/walkingkooka-tree-file.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/mP1/walkingkooka-tree-file/alerts/)



A [walkingkooka/tree.Node](https://github.com/mP1/walkingkooka/blob/master/Node.md) read only view of a file system files and directories, primarily interesting because of xpath selection [walkingkooka/tree.NodeSelector](https://github.com/mP1/walkingkooka/blob/master/src/main/java/walkingkooka/tree/select/NodeSelector.java)

# Sample

A working example is avaiable at [ReadmeSample.java](https://github.com/mP1/walkingkooka-tree-file/tree/master/src/test/java/walkingkooka/tree/file/ReadmeSample.java).

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
