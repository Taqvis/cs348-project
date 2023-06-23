LOAD DATA INFILE '/var/lib/mysql-files/dataset-sample-tracks.csv'
    INTO TABLE Tracks
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;

LOAD DATA INFILE '/var/lib/mysql-files/dataset-sample-artists.csv'
    INTO TABLE Track_Artists
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;