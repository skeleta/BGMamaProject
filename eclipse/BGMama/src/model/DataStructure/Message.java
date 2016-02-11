package model.DataStructure;

public class Message {
public String idmsg;
public String idtopic;
public String msgsubject;
public String msgbody;

@Override
public String toString() {
	return "Message [\nidmsg=" + idmsg + ", \nidtopic=" + idtopic + ", \nmsgsubject=" + msgsubject + ", \nmsgbody=" + msgbody
			+ "]";
}

}
