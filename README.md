### `min-log`
Wraps the `java` logger.

* replaces parent handlers with :
  * 1`ConsoleHandler`
  * 1 `FileHandler`
* adds _pretty_ display of:
  * thread name
  * method name
* can (should) customize with `LogU.remake(...)` :
  * log file directory
  * project package name
    * prefix, filters stack, to print method name, thereby skipping over the `min-log` methods.
    * admittedly, this is a slightly wonky approach.
  * format:
    * default: `"[%1$tF %1$tT] [%2$-7s] [%3$-20s] %4$-120s [%5$s]\n"`
