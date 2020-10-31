/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.file;

import walkingkooka.Either;
import walkingkooka.collect.map.Maps;
import walkingkooka.convert.FakeConverter;
import walkingkooka.predicate.Predicates;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.tree.expression.ExpressionNumberContexts;
import walkingkooka.tree.expression.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.ExpressionNumberConverterContexts;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.FunctionExpressionName;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.number.NumberExpressionFunctions;
import walkingkooka.tree.expression.function.string.StringExpressionFunctions;
import walkingkooka.tree.select.NodeSelector;
import walkingkooka.tree.select.NodeSelectorContexts;
import walkingkooka.tree.select.parser.NodeSelectorExpressionParserToken;
import walkingkooka.tree.select.parser.NodeSelectorParserContext;
import walkingkooka.tree.select.parser.NodeSelectorParserContexts;
import walkingkooka.tree.select.parser.NodeSelectorParsers;

import java.math.MathContext;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ReadmeSample {

    private final static ExpressionNumberKind KIND = ExpressionNumberKind.DEFAULT;

    /**
     * Extremely minimalist without checking required command line params are available, and other basics.
     */
    public static void main(final String[] args) throws Exception {
        System.out.println(Arrays.stream(args).collect(Collectors.joining(" ", "Command line args:\n", "\n")));

        final Path baseDir = Paths.get(args[0]);
        final String selector = args[1];
        final String containsText = args[2];

        // node selector parser
        final Parser<NodeSelectorParserContext> parser = NodeSelectorParsers.expression()
                .orReport(ParserReporters.basic())
                .cast();

        // parse into token then selector
        final NodeSelector<FilesystemNode, FilesystemNodeName, FilesystemNodeAttributeName, String> find = FilesystemNode.nodeSelectorExpressionParserToken(
                parser.parse(TextCursors.charSequence(selector), NodeSelectorParserContexts.basic(ExpressionNumberContexts.basic(KIND, MathContext.DECIMAL32)))
                        .map(NodeSelectorExpressionParserToken.class::cast)
                        .orElseThrow(() -> new Exception("Failed to parse selector")),
                Predicates.always());

        final FilesystemNodeContext filesystemNodeContext = FilesystemNodeContexts.basic(baseDir);

        // stream, filter if files contain arg[2] and then print matching files.
        find.stream(filesystemNodeContext.directory(baseDir),
                ReadmeSample::function,
                new FakeConverter<ExpressionNumberConverterContext>() {
                    @Override
                    public boolean canConvert(final Object value,
                                              final Class<?> type,
                                              final ExpressionNumberConverterContext context) {
                        return type.isInstance(value);
                    }

                    @Override
                    public <T> Either<T, String> convert(final Object value,
                                                         final Class<T> type,
                                                         final ExpressionNumberConverterContext context) {
                        return this.canConvert(value, type, context) ?
                                Either.left(type.cast(value)) :
                                this.failConversion(value, type);
                    }
                }, // many functions operate on strings converters convert values to strings.
                ExpressionNumberConverterContexts.fake(),
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

    private static Optional<ExpressionFunction<?>> function(final FunctionExpressionName name) {
        final Map<FunctionExpressionName, ExpressionFunction<?>> nameToFunction = Maps.sorted();

        NumberExpressionFunctions.visit((f) -> nameToFunction.put(f.name(), f));
        StringExpressionFunctions.visit(1, (f) -> nameToFunction.put(f.name(), f));

        final ExpressionFunction<?> function = nameToFunction.get(name);
        return null != function ?
                Optional.of(function) :
                NodeSelectorContexts.basicFunctions().apply(name);
    }
}
