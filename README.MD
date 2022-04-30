# PolarCore
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/cc0af3e7ffbe4ac89ed566aeae004d6c)](https://app.codacy.com/gh/saltedfishclub/PolarCore?utm_source=github.com&utm_medium=referral&utm_content=saltedfishclub/PolarCore&utm_campaign=Badge_Grade_Dashboard)
v4 refactored.

Not completed yet.
Open source as Apache2.0 License
## What
My bot's kernel.

## Use as a lib
```groovy
  maven { url 'https://jitpack.io' }
```
then
`compileOnly 'com.github.saltedfishclub:PolarCore:{{ VERSION }}`
If you want to use the latest build:
`compileOnly 'com.github.saltedfishclub:-SNAPSHOT`

## Structure
User Messages <-> Wrappers (Convert to CatCodes) <-> PolarCore
