
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