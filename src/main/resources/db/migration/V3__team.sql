CREATE TABLE team (
    id int unsigned NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    submission_url varchar(100),
    grades float(10),
    hackathon_id int unsigned,
    PRIMARY KEY (id),
    UNIQUE KEY (name),
    FOREIGN KEY (hackathon_id) references hackathon(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE team_membership (
    team_id int unsigned NOT NULL,
    member_id int unsigned NOT NULL,
    role varchar(20),
    fee_paid boolean,
    amount float(20),
    FOREIGN KEY (team_id) references team(id),
    FOREIGN KEY (member_id) references user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


