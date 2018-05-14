USE javabase;
DROP TABLE javabase.friends;
DROP TABLE javabase.ratings;
DROP TABLE javabase.stats;

CREATE TABLE javabase.stats (
	userID BIGINT UNSIGNED NOT NULL,

	FOREIGN KEY (userID) REFERENCES user (ID),
	PRIMARY KEY (userID)

);

CREATE TABLE javabase.friends (
	edgeID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  userID1 BIGINT UNSIGNED NOT NULL,
	userID2 BIGINT UNSIGNED NOT NULL,
	FOREIGN KEY (userID1) REFERENCES users (ID),
  FOREIGN KEY (userID2) REFERENCES users (ID),
	PRIMARY KEY (edgeID)
);
CREATE TABLE javabase.ratings (
	edgeID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  userID1 BIGINT UNSIGNED NOT NULL,
  userID2 BIGINT UNSIGNED NOT NULL,
  rating INTEGER UNSIGNED NOT NULL,

  CONSTRAINT self_ref CHECK (userID1 != userID2),

  FOREIGN KEY (userID1) REFERENCES users (ID),
  FOREIGN KEY (userID2) REFERENCES users (ID),
  PRIMARY KEY (edgeID)
);
