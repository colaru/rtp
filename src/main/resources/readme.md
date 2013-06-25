Start the sender first, then the handler:
vertx run sender.js -cluster
vertx run handler.js -cluster -cluster-port 25508

or

vertx run d:\WORK\REALTIME\rtp\src\main\resources\sender.js -cluster -cluster-host 192.168.2.17
vertx run d:\WORK\REALTIME\rtp\src\main\resources\handler.js -cluster -cluster-port 25508 -cluster-host 192.168.2.17 -instances 4

From vert.x documentation:

-cluster This option determines whether the vert.x server which is started will attempt to form a cluster with other vert.x instances on the network. Clustering vert.x instances allows vert.x to form a distributed event bus with other nodes. Default is false (not clustered).

-cluster-port If the cluster option has also been specified then this determines which port will be used for cluster communication with other vert.x instances. Default is 25500. If you are running more than one vert.x instance on the same host and want to cluster them, then you'll need to make sure each instance has its own cluster port to avoid port conflicts.

-cluster-host If the cluster option has also been specified then this determines which host address will be used for cluster communication with other vert.x instances. By default it will try and pick one from the available interfaces. If you have more than one interface and you want to use a specific one, specify it here.



