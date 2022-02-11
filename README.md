# ConfigSystem
Simpel Libary for handdling config-data. It's loaded and saved using JSON-format.

## Setting Values
Simply add new entries with Config#set(String, Object).
You can also add inner Config using Config#setInnerConfig(String, Config).

The String represents the value's key. You can create sections using "." to split your keys. 
E.g. "inner.key" will create an inner Config "inner" and add the value to it with the key "key". (Default's keys work the same way)

## Default-Values
You can add default values using Config#setDefault(String, Object). 
These will be loaded into the File and will be returned if not overwritten.

You can also get them (Config#getDefault(String)) and check if they exist (Config#containDefault(String)).

## Getting Values
Get a normal value using Config#get(String). You can also specify a default value using Config#get(String, Object).

Getting a List (List<?>) is also possible using Config#getList(String).

If you want to get a List of a specified type use the generic-methode Config#getList(String, Predicate<Object>, Function<Object, T>).
It will create a List using Config#getList(String) and then filter null-values and filter using the Predicate.
Then the Object values are converted using the given Function.

All of these getting related methodes try to get the value from config. If it does not exist, it will look for a default-value. 
If an default value is specified, it will be returned if there is neither a value in the config and as default-value matching the given key (String).

If you want to get a specified DataType as return type, you will have to use an DefiningConfig. 
This class extends Config and provides methodes 
 - DefiningConfig#get(String, IDataType), get value of specified DataType.
 - DefiningConfig#get(String, T, IDataType), get value of specified DataType or default value, T.
 - DefiningConfig#getList(String, IDataType), get List of specified DataType.
 
 There and IDataType can be optained from DataTypes-class.
 
## Inner Config
As already mentioned there can be multiple inner Config. These can be optained using the Config#get-methode. 
You also can cerate them using Config#CreatcreateInnerConfig(String). This methode returns an Config

## Key Set
The Key Set of all values and default values using Config#KeySet().
