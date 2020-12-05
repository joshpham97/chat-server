package app;

import server.database.model.Post;
import server.database.model.PostList;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class PostTransformer {
    public static String toXMLDocument(PostList lstPost) {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("<posts>");
        for(Post post: lstPost.getPosts()){
            strBuilder.append(PostTransformer.renderPostInXML(post));
        }
        strBuilder.append("</posts>");

        return strBuilder.toString();
    }

    public static String renderPostInXML(Post post){
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("<post>");
        strBuilder.append("<username>").append(post.getUsername()).append("</username>");
        strBuilder.append("<title>").append(post.getTitle()).append("</title>");
        strBuilder.append("<datePosted>").append(post.getDatePostedStr()).append("</datePosted>");
        strBuilder.append("<dateModified>").append(post.getDateModifiedStr()).append("</dateModified>");
        strBuilder.append("<message>").append(post.getMessage()).append("</message>");
        strBuilder.append("<datePosted>").append(post.getDatePostedStr()).append("</datePosted>");
        strBuilder.append("</post>");

        return strBuilder.toString();
    }
}
