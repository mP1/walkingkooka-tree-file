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

import walkingkooka.collect.iterator.Iterators;
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.ImmutableSetDefaults;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * A readonly {@link Set} view of entries belonging to the attribute Map view for a {@link FilesystemNode}.
 */
final class FilesystemNodeAttributeMapEntrySet extends AbstractSet<Entry<FilesystemNodeAttributeName, String>>
    implements ImmutableSetDefaults<FilesystemNodeAttributeMapEntrySet, Entry<FilesystemNodeAttributeName, String>> {

    static FilesystemNodeAttributeMapEntrySet with(final FilesystemNode node) {
        return new FilesystemNodeAttributeMapEntrySet(node);
    }

    private FilesystemNodeAttributeMapEntrySet(final FilesystemNode node) {
        this.node = node;
    }

    @Override
    public Iterator<Entry<FilesystemNodeAttributeName, String>> iterator() {
        return Iterators.mapping(node.attributeNames().iterator(), this::iteratorMapper);
    }

    private Entry<FilesystemNodeAttributeName, String> iteratorMapper(final FilesystemNodeAttributeName name) {
        return Maps.entry(name, name.read(this.node));
    }

    @Override
    public int size() {
        return this.node.attributeNames().size();
    }

    private final FilesystemNode node;

    @Override
    public void elementCheck(final Entry<FilesystemNodeAttributeName, String> entry) {
        Objects.requireNonNull(entry, "entry");
    }

    @Override
    public FilesystemNodeAttributeMapEntrySet setElements(final Collection<Entry<FilesystemNodeAttributeName, String>> elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<FilesystemNodeAttributeName, String>> toSet() {
        throw new UnsupportedOperationException();
    }
}
