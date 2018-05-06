import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RssFeedProducer {

    public static void main(String[] args) throws IOException, InterruptedException{

        // Verifier que le topic est donne en argument
        if(args.length == 0){
            System.out.println("Entrer le nom du topic");
            return;
        }

        // Assigner topicName a une variable
        String topicName = args[0].toString();

        // Creer une instance de proprietes pour acceder aux configurations du producteur
        Properties props = new Properties();

        // Assigner l'identifiant du serveur kafka
        props.put("bootstrap.servers", "localhost:9092");

        // Definir un acquittement pour les requetes du producteur
        props.put("acks", "all");

        // Si la requete echoue, le producteur peut reessayer automatiquemt
        props.put("retries", 0);

        // Specifier la taille du buffer size dans la config
        props.put("batch.size", 16384);

        // buffer.memory controle le montant total de memoire disponible au producteur pour le buffering
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer
                <String, String>(props);

        // Open the file
        FileInputStream fstream = new FileInputStream("rssFeed.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            RSSFeedParser parser = new RSSFeedParser(strLine);
            Feed feed = parser.readFeed();
            int i=0;
            for (FeedMessage message : feed.getMessages()) {
                producer.send(new ProducerRecord<String, String>(topicName,
                        Integer.toString(i), message.toString()));
                i++;
		Thread.sleep(100);
            }
	Thread.sleep(1000);
        }

        System.out.println("Message envoye avec succes");
        producer.close();
    }
}

