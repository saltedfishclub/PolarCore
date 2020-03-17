# PolarCore

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/cc0af3e7ffbe4ac89ed566aeae004d6c)](https://app.codacy.com/gh/saltedfishclub/PolarCore?utm_source=github.com&utm_medium=referral&utm_content=saltedfishclub/PolarCore&utm_campaign=Badge_Grade_Dashboard)

Not completed yet.  
Open source as LGPLv3
## What
A bot kernel.  
The wonder Polar(??? Bot)'s Kernel Source!!  

## Use as a lib
```groovy
  maven { url 'https://jitpack.io' }
```
then  
`implementation 'cc.sfclub:PolarCore:1.1.1`  
If you want to use the latest build:  
`implementation 'cc.sfclub:PolarCore:1.1.1-SNAPSHOT`

## Structure
Message Flow:  
`Core < - > Wrapper < - > Platfrom/Provider`
Used libraries:  
NutDao,lombok,GSon,Reflections,Druid,Slf4j,Logback,EventBus

## Demo
Polar ???:0
(Interaction should be in a Group,like `0(???)`)
keep running on latest PolarCore. 

## SpecialThanks
@MeiVinEight ~ Plugin System