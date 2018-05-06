# RSS-Feed-Kafka-Producer
get the rss feeds from different sites and then producing them to a kafka topic
 #### apache-kafka and zookeeper must be installed : 
 ###### https://kafka.apache.org/ 
 ###### https://zookeeper.apache.org/releases.html
 #### 1- clone the project 
 #### 1- javac -cp "$KAFKA_HOME/libs/*":. *.java
 #### 2- java -cp "$KAFKA_HOME/libs/*":. RssFeedProducer kafka_topic
 ##### it's possible to use the kafka default consumer to visualize the messsages produced to the kafka topic 
 ###### kafka-console-consumer.sh --zookeeper localhost:2181 --topic kafka_topic --from-beginning
