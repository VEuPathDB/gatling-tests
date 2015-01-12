
### Experimental Gatling tests

Download, untar Gatling - [http://gatling.io/download](http://gatling.io/download/)

Add to `$PATH`

    export PATH=$PATH:~/gatling-charts-highcharts-bundle-2.1.2/bin

Acquire tests

    git clone git@github.com/EuPathDB/gatling-tests

    cd gatling-tests
    
Run, with interactive menu choice,

    bin/grun

or, for single test class,

    bin/grun -s GeneTextSearch
    
    
----

### Logging

The conf/logback.xml is customized to allow changes to log levels by setting
Java system properties on the commandline.

#### Log ALL HTTP request and responses to console.

        JAVA_OPTS="-DhttpLogging=TRACE" bin/grun

#### Log  ONLY FAILED HTTP request and responses to console.

        JAVA_OPTS="-DhttpLogging=DEBUG" bin/grun

#### Set log level for Gatling root appender.

        JAVA_OPTS="-DrootLogging=DEBUG" bin/grun

#### Combining log levels

        JAVA_OPTS="-DrootLogging=TRACE -DhttpLogging=TRACE" bin/grun -s wdk.StrategyMix 

See
[LOGBack FAQ](http://logback.qos.ch/faq.html#overrideFromCL) for more 
information.