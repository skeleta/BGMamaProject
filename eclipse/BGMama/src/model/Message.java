package model;

public class Message {
public String idmsg;
public String idtopic;
public String msgsubject;
public String msgbody;



public String getMsgsubject() {
	return htmlToPlainText(msgsubject);
}



public String getMsgbody() {
	return htmlToPlainText(msgbody);
}

private String htmlToPlainText(String html) {
	String text = html.replaceAll("\\<[^>]*>|&[^;]*;","");	
	text = text.replaceAll("\\[[^\\]]*]", "");
	text = text.replaceAll("\\:[^:]*:", "");
    return text;
}



@Override
public String toString() {
	return "Message [\nidmsg=" + idmsg + ", \nidtopic=" + idtopic + ", \nmsgsubject=" + msgsubject + ", \nmsgbody=" + msgbody
			+ "]";
}

}
