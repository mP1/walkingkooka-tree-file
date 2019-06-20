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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A {@link FilesystemNodeContext} with no caching of any method with a fixed root. With a root set it will not be able
 * to navigate outside that sub tree.
 */
final class BasicFilesystemNodeContext implements FilesystemNodeContext {

    /**
     * Creates a new {@link BasicFilesystemNodeContext} failing if the root/directory does not exist.
     */
    static BasicFilesystemNodeContext with(final Path rootPath) {
        Objects.requireNonNull(rootPath, "rootPath");

        if(!Files.isDirectory(rootPath)) {
            throw new IllegalArgumentException("Directory \"" + rootPath.toAbsolutePath() + "\" not found ");
        }

        return new BasicFilesystemNodeContext(rootPath);
    }

    private BasicFilesystemNodeContext(final Path rootPath) {
        super();
        this.rootPath = rootPath;
    }

    @Override
    public Path rootPath() {
        return this.rootPath;
    }

    private final Path rootPath;

    @Override
    public FilesystemNode directory(final Path path) {
        return FilesystemNode.directory(path, this);
    }

    @Override
    public FilesystemNode file(final Path path) {
        return FilesystemNode.file(path, this);
    }

    @Override
    public boolean mustLoad(final FilesystemNode node, final FilesystemNodeCacheAtom atom) {
        return true; // always load
    }

    @Override
    public String text(final Path path) throws IOException {
        final char[] buffer = new char[4096];
        final StringBuilder text = new StringBuilder();
        try (final Reader reader = new InputStreamReader(new FileInputStream(path.toFile()))) {
            for (; ; ) {
                final int read = reader.read(buffer);
                if (-1 == read) {
                    break;
                }
                text.append(buffer, 0, read);
            }
        }
        return text.toString();
    }

    @Override
    public String toString() {
        return this.rootPath().toAbsolutePath().toString();
    }
}
