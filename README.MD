# PolarCore
Not completed yet.  
Open source as LGPLv3

# What?
A bot kernel.  
The wonder Polar(QQ Bot)'s Kernel Source!!  

# Use as a lib
```groovy
  maven { url 'https://jitpack.io' }
```
then  
`implementation 'cc.sfclub:PolarCore:1.1.1`
If you want to use the latest build:  
`implementation 'cc.sfclub:PolarCore:1.1.1-SNAPSHOT`

# Structure
Message Flow:  
`Core < - > Wrapper < - > Platfrom/Provider`
Used libraries:  
NutDao,lombok,GSon,Reflections,Druid,Slf4j,Logback,EventBus

# Demo
Polar QQ:3325273573
(Interaction should be in a Group,like `443098623(QQ)`)
keep on latest PolarCore. 

# SpecialThanks
@MeiVinEight ~ Plugin System