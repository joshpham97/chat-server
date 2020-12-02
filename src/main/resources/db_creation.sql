CREATE TABLE Attachments(
    att_id INT NOT NULL AUTO_INCREMENT,
    filename VARCHAR(255) NOT NULL,
    file_size INT NOT NULL,
    file_type VARCHAR(255),
    attachment LONGBLOB NOT NULL,
    CONSTRAINT PK_att_id PRIMARY KEY(att_id)
);

CREATE TABLE Post_info (
   post_id INT NOT NULL AUTO_INCREMENT,
   username VARCHAR(255) NOT NULL,
   title VARCHAR(255),
   date_posted DATETIME,
   date_modified DATETIME,
   message VARCHAR(255),
   att_id INT,
   CONSTRAINT PK_post_id PRIMARY KEY (post_id),
   CONSTRAINT FK_att_id FOREIGN KEY (att_id) REFERENCES Attachments(att_id)
);

CREATE TABLE Hashtag(
    hashtag_id int NOT NULL AUTO_INCREMENT,
    hashtag varchar(255) NOT NULL,
    CONSTRAINT PK_hashtag_id PRIMARY KEY(hashtag_id)
);

CREATE TABLE Post_Hashtag(
     post_hashtag_id INT NOT NULL AUTO_INCREMENT,
     post_id INT NOT NULL,
     hashtag_id int NOT NULL,
     CONSTRAINT PK_post_id_hashtag PRIMARY KEY(post_hashtag_id),
     CONSTRAINT FK_post_id FOREIGN KEY(post_id) REFERENCES Post_info(post_id),
     CONSTRAINT FK_hashtag_id FOREIGN KEY(hashtag_id) REFERENCES Hashtag(hashtag_id)
);
 