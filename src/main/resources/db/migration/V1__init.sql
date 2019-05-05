
CREATE TABLE user (
    id int unsigned NOT NULL AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email varchar(255) NOT NULL ,
    screen_name varchar(255) NOT NULL,
    portrait_url varchar(255),
    business_title varchar(255),
    about_me text,
    street varchar(100),
    city varchar(100),
    state varchar(25),
    zip varchar(10),
    is_validated boolean NOT NULL default false,
    is_admin boolean NOT NULL default false,
    PRIMARY KEY (id),
    UNIQUE KEY (email),
    UNIQUE KEY (screen_name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE organization (
    id int unsigned NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    description text,
    street varchar(100),
    city varchar(100),
    state varchar(25),
    zip varchar(10),
    owner_id int unsigned,
    PRIMARY KEY (id),
    UNIQUE KEY (name),
    FOREIGN KEY (owner_id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE organization_membership (
    organization_id int unsigned NOT NULL,
    member_id int unsigned NOT NULL,
    status enum('Pending', 'Approved', 'Left', 'Rejected') NOT NULL,
    FOREIGN KEY (organization_id) references organization(id),
    FOREIGN KEY (member_id) references user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


