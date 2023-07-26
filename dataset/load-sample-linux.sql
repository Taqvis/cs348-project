LOAD DATA LOCAL INFILE '/Users/ataqvi/Documents/MyCode/cs348-project/dataset/dataset-sample-tracks.csv'
    INTO TABLE Tracks
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;

LOAD DATA LOCAL INFILE '/Users/ataqvi/Documents/MyCode/cs348-project/dataset/dataset-sample-artists.csv'
    INTO TABLE Track_Artists
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;