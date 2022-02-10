package de.borekking.configSystem.dataTypes;

class CharacterDataType implements IDataType<Character> {

    @Override
    public Character convert(Object o) {
        return (char) o;
    }

    @Override
    public boolean test(Object o) {
        return o instanceof Character;
    }

    @Override
    public Character getDef() {
        return Character.MIN_VALUE;
    }
}
