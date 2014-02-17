Belkin WeMo Insight Switch Wrapper
==================================

A simple library for controlling Belkin WeMo Insight switches from Java. Getting started is easy and fun. Just add a dependency to your `pom.xml`:

```xml
<repository>
  <id>maven-snapshots</id>
  <name>Maven Snapshots</name>
  <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
  <snapshots><enabled>true</enabled></snapshots>
</repository>
...
<dependency>
  <groupId>com.palominolabs.wemo</groupId>
  <artifactId>wemo</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

And have yourself a disco rave party:

```java
String switchFriendlyName = "Floodlight";
try (InsightSwitchFinder insightSwitchFinder = new InsightSwitchFinder(switchFriendlyName)) {
    if (!insightSwitchFinder.findSwitches()) {
        throw new IllegalStateException("Unable to find switches");
    }
 
    InsightSwitch insightSwitch = insightSwitchFinder.getSwitch(switchFriendlyName);
 
    while (true) {
        insightSwitch.switchOn();
        Thread.sleep(100);
        insightSwitch.switchOff();
        Thread.sleep(100);
    }
}
```