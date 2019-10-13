package sqs;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class customer {
	private static Scanner sc;

	public static void main(String[] args) throws InterruptedException
	{
		while(true) {
		sc = new Scanner(System.in);
		String str=null;
		System.out.print("message you want to send:");
		str=sc.nextLine();
	    System.out.println("message you send is:"+str);
	    AmazonSQS Sqs =AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
	    String url = Sqs.createQueue(new CreateQueueRequest("a_queue")).getQueueUrl();
	    Sqs.sendMessage(new SendMessageRequest(url, str));
	    while (true) {
		  List<Message> msgs = Sqs.receiveMessage(
		     new ReceiveMessageRequest(url).withMaxNumberOfMessages(1)).getMessages();
		if (msgs.size() > 0) {
		   Message message = msgs.get(0);
		   System.out.println("The message is " + message.getBody());
		   Sqs.deleteMessage(new DeleteMessageRequest(url, message.getReceiptHandle()));
		   break;
		  } 
		break;
		}
	}
  }
}


