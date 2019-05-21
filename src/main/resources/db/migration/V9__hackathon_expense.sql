CREATE TABLE hackathon_expense (
   id int unsigned NOT NULL AUTO_INCREMENT,
   hackathon_id int unsigned NOT NULL,
   title varchar(255) NOT NULL,
   description text,
   date date NOT NULL,
   amount float NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (hackathon_id) references hackathon(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;