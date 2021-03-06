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

import walkingkooka.test.Fake;

import java.nio.file.Path;

public class FakeFilesystemNodeContext implements FilesystemNodeContext, Fake {

    protected FakeFilesystemNodeContext() {
    }

    @Override
    public Path rootPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilesystemNode entry(final Path path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilesystemNode directory(final Path path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FilesystemNode file(final Path path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean mustLoad(final FilesystemNode node, final FilesystemNodeCacheAtom atom) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String text(final Path path) throws Exception {
        throw new UnsupportedOperationException();
    }
}
