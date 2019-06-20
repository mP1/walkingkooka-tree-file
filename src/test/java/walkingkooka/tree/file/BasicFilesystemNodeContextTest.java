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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicFilesystemNodeContextTest implements FilesystemNodeContextTesting<BasicFilesystemNodeContext> {

    @Test
    public void testWithNullBasePathFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicFilesystemNodeContext.with(null);
        });
    }

    @Test
    public void testWithInvalidBasePathFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            BasicFilesystemNodeContext.with(Paths.get(this.tempDir.toString(), "invalid"));
        });
    }

    @Test
    public void testDirectory() throws IOException {
        final String name = "dir123";
        final Path sub = Files.createDirectory(Paths.get(tempDir.toString() + File.pathSeparatorChar + name));
        final BasicFilesystemNodeContext context = this.createContext();
        this.check(context.directory(sub), sub, context);
    }

    @Test
    public void testFile() throws IOException {
        final String name = "file123";
        final Path file = Files.createFile(Paths.get(tempDir.toString() + File.pathSeparatorChar + name));
        final BasicFilesystemNodeContext context = this.createContext();
        this.check(context.directory(file), file, context);
    }

    private void check(final FilesystemNode node, final Path path, final BasicFilesystemNodeContext context) {
        assertSame(path, node.path, "path");
        assertSame(context, node.context, "context");
    }

    @Test
    public void testText() throws IOException {
        final String name = "file123";
        final Path file = Files.createFile(Paths.get(tempDir.toString() + File.pathSeparatorChar + name));

        final String content = "content-abc123";
        Files.write(file, content.getBytes(Charset.defaultCharset()));

        final BasicFilesystemNodeContext context = this.createContext();
        assertEquals(content, context.text(file), () -> "text " + file);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createContext(), this.tempDir.toAbsolutePath().toString());
    }

    @TempDir
    Path tempDir;

    @Override
    public BasicFilesystemNodeContext createContext() {
        return BasicFilesystemNodeContext.with(this.tempDir);
    }

    @Override
    public Class<BasicFilesystemNodeContext> type() {
        return BasicFilesystemNodeContext.class;
    }
}
