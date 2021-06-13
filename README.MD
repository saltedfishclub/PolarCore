# PolarCore
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/cc0af3e7ffbe4ac89ed566aeae004d6c)](https://app.codacy.com/gh/saltedfishclub/PolarCore?utm_source=github.com&utm_medium=referral&utm_content=saltedfishclub/PolarCore&utm_campaign=Badge_Grade_Dashboard).  
v4 refactored.  

Under Heavy Development.  
Open source / Apache2.0 License
## What  
A Chatbot kernel which had built-in User,Permission,SpecialMessages,Command systems.  
Also,It support to use `adapters` to warp diffirient messages into polarCore's.


## Dependency
```groovy
  maven { url 'https://jitpack.io' }
```
and
`compileOnly 'com.github.saltedfishclub:PolarCore:{{ VERSION }}`
If you want to use the latest build:
`compileOnly 'com.github.saltedfishclub:dev-SNAPSHOT`

## How messages process
User Messages from diffirient platforms <-> Adapter (Convert to CatCodes) <-> PolarCore and plugins

## Demo
Polar :0 (??? UIN)
(Interaction should be in a Group,e.g `0(???)`)
keep running on latest PolarCore.
