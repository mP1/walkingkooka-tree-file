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

import walkingkooka.collect.set.ImmutableSetTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;

public final class FilesystemNodeAttributeMapEntrySetTest implements ClassTesting2<FilesystemNodeAttributeMapEntrySet>,
        ImmutableSetTesting<FilesystemNodeAttributeMapEntrySet, Entry<FilesystemNodeAttributeName, String>> {

    @Override
    public FilesystemNodeAttributeMapEntrySet createSet() {
        return FilesystemNodeAttributeMapEntrySet.with(this.createNode());
    }

    private FilesystemNode createNode() {
        final Path home = Paths.get(".");
        return FilesystemNode.directory(home, new FakeFilesystemNodeContext() {

            @Override
            public Path rootPath() {
                return home;
            }

            @Override
            public boolean mustLoad(final FilesystemNode node, final FilesystemNodeCacheAtom atom) {
                return true;
            }
        });
    }

    @Override
    public void testSetElementsNullFails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testSetElementsSame() {
        throw new UnsupportedOperationException();
    }

    // class............................................................................................................

    @Override
    public Class<FilesystemNodeAttributeMapEntrySet> type() {
        return FilesystemNodeAttributeMapEntrySet.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
