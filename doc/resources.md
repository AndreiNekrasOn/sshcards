# Enemy

Each enemy is represented as a JSON-file, an ASCII art and the class, describing its' behavior.

ASCII art has the following structure:

```ascii
  |   _      _     |
  |  |_|    _ |    |
   \        | |   /
    \__-----|x|__/



```

Note, that the height of 6 rows and maximum width of 20 columns is significant. The lines do not have trailing spaces, so the maximum width is not always reached. The height, however, should always be constant.

JSON-file describes base states of the enemy. It has the following structure:

```
name="Regular Ship"
resource="tui/enemy/RegularShip"
hp=20
maxHp=20
dmg=1
armor=1
misc.exampleProp=1
```

Resource field should reference the ascii art of the enemy. This is structured that way to allow moving towards graphical UI as a possibility.
"hp" and "maxHp" are separated because the enemy may start damaged, to allow healing mechanics.
Fields "dmg" and "armor" are referenced by the enemy's logic in `.java` file.
Finally, the "misc" field allows for any kind of extension, since the enemy's logic might require different base values, not foreseen in the specification.The extensions are read into a Map, saved in the misc field
