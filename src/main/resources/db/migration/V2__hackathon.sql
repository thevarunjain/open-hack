CREATE TABLE hackathon (
  id int unsigned NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  start_date date NOT NULL,
  end_date date NOT NULL,
  description varchar(255) NOT NULL,
  fee float NOT NULL,
  max_size int(11) NOT NULL,
  min_size int(11) NOT NULL,
  status enum('Open', 'Closed', 'Finalized') DEFAULT 'Open',
  PRIMARY KEY (id),
  UNIQUE KEY (name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE hackathon_judge (
  hackathon_id int unsigned NOT NULL,
  judge_id int unsigned NOT NULL,
  PRIMARY KEY (hackathon_id,judge_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE hackathon_sponsor (
  hackathon_id int unsigned  NOT NULL,
  sponsor_id int unsigned  NOT NULL,
  discount int(11) NOT NULL,
  PRIMARY KEY (hackathon_id,sponsor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


