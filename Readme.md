# Finding last common commits for two branches
## it's analogue of ```git merge-base A B --all```

### One common commit case:
```
    A
    |\
    | \
    |  \
    |   *
    |   |
B   |   |
*   *   *
|   |  /
|   | /
|   |/
*   *
|  /
| /
|/
*
|
|
|
*


*! - last common commit
```

### Several common commits case:
``` 
     B
     *
     |\
     | \
     |  \
     *   \
    /||   \
   / \\    \
  /   \\    \
 /     \\    *
/    A | |   |
|   *  | |   |
|  /|\ | |   |
| / | \/ |   |
|/  | /\ |   |
|/  |/  \|   |
*!  *!  *!   |
 \  |  /     | 
  \ | /      *
   \|/       /
    |       /
    |      /
    |     /
    *    /
    |   /
    |  /
    | /
    */
    |
    |
    |
    *
    
*! - last common commits
```