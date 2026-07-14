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

import walkingkooka.collect.set.Sets;
import walkingkooka.naming.Name;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;

import java.util.Objects;
import java.util.Set;

/**
 * A file or directory name. Note case-sensitivity matches the same rules of the underlying filesystem.
 * A directory has the following attributes: CREATED, GROUP, HIDDEN, LAST_ACCESSED, LAST_MODIFIED, OWNER, TYPE
 * A file has the following attributes: CREATED, GROUP, HIDDEN, LAST_ACCESSED, LAST_MODIFIED, OWNER, SIZE, TEXT, TYPE
 */
public abstract class FilesystemNodeAttributeName implements Name,
    Comparable<FilesystemNodeAttributeName> {

    /**
     * An ISO_FORMATTED timestamp of the creation, taken from {@link java.nio.file.attribute.BasicFileAttributes}
     * {@see java.time.format.DateTimeFormatter}
     */
    public final static FilesystemNodeAttributeName CREATED = new FilesystemNodeAttributeName("CREATED") {
        @Override
        String read(final FilesystemNode node) {
            return node.created();
        }
    };

    /**
     * A String holding either the boolean value of true of false using {@link java.nio.file.Files#isHidden}.
     */
    public final static FilesystemNodeAttributeName HIDDEN = new FilesystemNodeAttributeName("HIDDEN") {
        @Override
        String read(final FilesystemNode node) {
            return node.hidden();
        }
    };

    /**
     * An ISO_FORMATTED timestamp of the last access, taken from {@link java.nio.file.attribute.BasicFileAttributes}
     * {@see java.time.format.DateTimeFormatter}
     */
    public final static FilesystemNodeAttributeName LAST_ACCESSED = new FilesystemNodeAttributeName("LAST_ACCESSED") {
        @Override
        String read(final FilesystemNode node) {
            return node.lastAccessed();
        }
    };

    /**
     * An ISO_FORMATTED timestamp of the last modification, taken from {@link java.nio.file.attribute.BasicFileAttributes}
     * {@see java.time.format.DateTimeFormatter}
     */
    public final static FilesystemNodeAttributeName LAST_MODIFIED = new FilesystemNodeAttributeName("LAST_MODIFIED") {
        @Override
        String read(final FilesystemNode node) {
            return node.lastModified();
        }
    };

    /**
     * The owner, taken from {@link java.nio.file.attribute.PosixFileAttributes}
     */
    public final static FilesystemNodeAttributeName OWNER = new FilesystemNodeAttributeName("OWNER") {
        @Override
        String read(final FilesystemNode node) {
            return node.owner();
        }
    };

    /**
     * The size of the file in bytes
     * (File only attribute)
     */
    public final static FilesystemNodeAttributeName SIZE = new FilesystemNodeAttributeName("SIZE") {
        @Override
        String read(final FilesystemNode node) {
            return node.size();
        }
    };

    /**
     * The text attribute holds the text for a given file.
     * (File only attribute)
     */
    public final static FilesystemNodeAttributeName TEXT = new FilesystemNodeAttributeName("TEXT") {
        @Override
        String read(final FilesystemNode node) {
            return node.text();
        }
    };

    /**
     * A String value that currently holds either: FILE or DIRECTORY.
     */
    public final static FilesystemNodeAttributeName TYPE = new FilesystemNodeAttributeName("TYPE") {
        @Override
        String read(final FilesystemNode node) {
            return node.type();
        }
    };

    FilesystemNodeAttributeName(final String name) {
        super();
        this.name = name;
    }

    abstract String read(final FilesystemNode node);

    // HasValue.........................................................................................................

    @Override
    public final String value() {
        return this.name;
    }

    private final String name;

    // HasCaseSensitivity................................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CaseSensitivity.SENSITIVE;
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final FilesystemNodeAttributeName other) {
        return this.caseSensitivity()
            .comparator()
            .compare(
                this.name,
                other.name
            );
    }

    // toString.........................................................................................................

    @Override
    public String toString() {
        return this.name;
    }

    // ALL..............................................................................................................

    public final static Set<FilesystemNodeAttributeName> ALL = Sets.of(
        FilesystemNodeAttributeName.CREATED,
        FilesystemNodeAttributeName.HIDDEN,
        FilesystemNodeAttributeName.LAST_ACCESSED,
        FilesystemNodeAttributeName.LAST_MODIFIED,
        FilesystemNodeAttributeName.OWNER,
        FilesystemNodeAttributeName.SIZE,
        FilesystemNodeAttributeName.TEXT,
        FilesystemNodeAttributeName.TYPE
    );

    // valueOf..........................................................................................................

    public static FilesystemNodeAttributeName valueOf(final String name) {
        Objects.requireNonNull(name, "name");

        return ALL.stream()
            .filter((FilesystemNodeAttributeName filesystemNodeAttributeName) -> filesystemNodeAttributeName.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown FilesystemNodeAttributeName " + CharSequences.quote(name)));
    }
}
