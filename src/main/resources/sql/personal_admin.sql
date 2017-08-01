
CREATE TABLE IF NOT EXISTS `charge` (
  `id` varchar(64) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `label` varchar(64) NOT NULL COMMENT '标签，收支的分类的id',
  `mark` varchar(255) NULL COMMENT '备注',
  `type` enum('disbursements','receipts') NOT NULL DEFAULT 'disbursements' COMMENT 'receipts and disbursements： 收入与支出',
  `user_id` varchar(64) NOT NULL,
  `create_time` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `assignment` (
  `id` varchar(64) NOT NULL,
  `content` varchar(255) NOT NULL,
  `stare` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否重点关注',
  `completed` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否完成',
  `create_time` timestamp NOT NULL ,
  `user_id` varchar(54) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `charge_label` (
`id`  varchar(64) NOT NULL ,
`name`  varchar(100) NOT NULL ,
`mark`  varchar(255) NULL ,
`create_time`  timestamp NOT NULL ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `admin` (
`id`  varchar(64) NOT NULL ,
`name`  varchar(64) NOT NULL ,
`password`  varchar(64) NOT NULL ,
`create_time`  timestamp NOT NULL,
`last_login_time`  timestamp NULL,
`token`  varchar(64) NULL ,
`expire_time` bigint NULL ,
`avatar`  varchar(255) NULL ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `apples` (
  `id`  int NOT NULL AUTO_INCREMENT ,
  `weight`  int NOT NULL DEFAULT 0 ,
  `create_time`  timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP ,
  `user_id`  varchar(32) NOT NULL ,
  `is_eaten`  tinyint NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;