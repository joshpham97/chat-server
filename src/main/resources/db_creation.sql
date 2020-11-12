CREATE TABLE `message_board`.`attachment` (
  `attachment_id` INT NOT NULL AUTO_INCREMENT,
  `file_size` INT NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `file_type` VARCHAR(255) NOT NULL,
  `post_id` INT NOT NULL,
  `content` BLOB NOT NULL,
  PRIMARY KEY (`attachment_id`))
ENGINE = InnoDB
COMMENT = 'Table for attachments';