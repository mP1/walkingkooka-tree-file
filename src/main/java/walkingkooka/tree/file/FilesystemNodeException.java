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

import walkingkooka.tree.NodeException;

/**
 * Used to report or wrap any exceptions thrown while performing file operations.
 */
public class FilesystemNodeException extends NodeException {

    private static final long serialVersionUID = 1L;

    protected FilesystemNodeException() {
        super();
    }

    public FilesystemNodeException(final String message) {
        super(message);
    }

    public FilesystemNodeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
