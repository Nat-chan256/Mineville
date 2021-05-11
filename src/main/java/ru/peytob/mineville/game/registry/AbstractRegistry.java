package ru.peytob.mineville.game.registry;

import java.util.Hashtable;
import java.util.Map;

abstract class AbstractRegistry<T extends AbstractRegistrable> {
    private final Map<Short, T> registryIds;
    private final Map<String, T> registryText;

    public AbstractRegistry() {
        registryIds = new Hashtable<>();
        registryText = new Hashtable<>();
    }

    public T get(Short _id) {
        return registryIds.get(_id);
    }

    public T get(String _identifier) {
        return registryText.get(_identifier);
    }

    public RegistryModifier getRegistryModifier() {
        return new RegistryModifier();
    }
 
    public class RegistryModifier {
        public RegistryModifier() {

        }

        public boolean delete(Short _id) {
            return delete(get(_id));
        }

        public boolean delete(String _name) {
            return delete(get(_name));
        }

        public boolean delete(T _target) {
            T fromId = registryIds.remove(_target.getId());
            T fromText = registryText.remove(_target.getTextId());
            return fromId != null || fromText != null;
        }

        public void add(T object) {
            registryIds.put(object.getId(), object);
            registryText.put(object.getTextId(), object);
        }
    }
}
