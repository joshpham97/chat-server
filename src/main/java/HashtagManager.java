import server.chat.daoimpl.HashtagDAO;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashtagManager {
    public static void createHashTag(int postID, String message){
        HashtagDAO hashtagDao = new HashtagDAO();
        Set<String> hashtags = getHashtags(message);
        Iterator<String> it = hashtags.iterator();

        if(!hashtags.isEmpty()) {
            while(it.hasNext()) {
                String hashtagWord = it.next();
                hashtagDao.insertHashtag(postID, hashtagWord);
            }
        }
    }
    public static Set<String> getHashtags(String message) {
        String[] words = message.split("\\s+");
        Set<String> hashtags = new HashSet<String>();
        for (String word : words) {
            if (word.startsWith("#")) {
                hashtags.add(word.substring(1));
            }
        }
        return hashtags;
    }
}
