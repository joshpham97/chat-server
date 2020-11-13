package app;

import server.database.dao.HashtagDAO;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashtagManager {
    public static boolean createHashTag(int postID, String message){
        Set<String> hashtags = getHashtags(message);
        Iterator<String> it = hashtags.iterator();
        boolean success = true;

        if(!hashtags.isEmpty()) {
            while(it.hasNext() && success) {
                String hashtagWord = it.next();

                Hashtag hashtag = HashtagDAO.selectByHashtag(hashtagWord);

                Integer hashtagId;
                if (hashtag == null)
                    hashtagId = HashtagDAO.insert(postID, hashtagWord);
                else
                    hashtagId = hashtag.getId();

                if (hashtagId != null){
                    success = HashtagDAO.insertPostHashTag(postID, hashtagId);
                }
            }
        }

        return success;
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
