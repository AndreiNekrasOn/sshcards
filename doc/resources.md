# Enemy

Each enemy is represented as a text-file, an ASCII art and the class, describing its' behavior.

ASCII art has the following structure:

```ascii
  |   _      _     |
  |  |_|    _ |    |
   \        | |   /
    \__-----|x|__/



```

Note, that the height of 6 rows and maximum width of 20 columns is significant. The lines do not have trailing spaces, so the maximum width is not always reached. The height, however, should always be constant.

Text-file describes base states of the enemy. It has the following structure:

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

# Cards

For some reason I decided to do JSON for cards, probably because of array of actions. Cards don't have special logic, everything they can do is specified by actions. Actions, on the other hand, do contain implementations.

```json
{
    "name": "Shot",
    "description": "Deal 1 damaege",
    "cost": 1,
    "type": "attack",
    "art": "tui/cards/Shot",
    "actions": [
        {
            "type": "attack",
            "target": "enemy",
            "value": 1,
            // "payload": "optional"
        }
    ]
}
```

- cost - is energy cost
- type - can be an attack/skill/status. Possibly missiles will also be added here.
- art - points to the resource folder with art for this card.
- actions - array of actions that this card does.
- actions.action.type - attack/defence/effect/draw_skill/draw_attack
- actions.action.target - enemy/all/self/no, all stands for all enemies
- actions.action.value - the value of the performed action, e.g. attack for 1.


