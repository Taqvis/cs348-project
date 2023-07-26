CREATE TABLE IF NOT EXISTS Users (
	username VARCHAR(36) NOT NULL,
    display_name VARCHAR(36) NOT NULL,
    password VARCHAR(60) NOT NULL CHECK (LENGTH(password) >= 8),
    tier INT,
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS Playlists (
	username VARCHAR(36) NOT NULL,
    playlist_name VARCHAR(36) NOT NULL,
    PRIMARY KEY (username, playlist_name), 
    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Tracks (
    track_id VARCHAR(30) NOT NULL,
    album_name VARCHAR(254) NOT NULL,
    track_name VARCHAR(254) NOT NULL,
    popularity INT NOT NULL, CHECK (0 <= popularity AND popularity <= 100),
    duration_ms INT,
    explicit BOOLEAN,
    danceability DECIMAL(16,15) CHECK (danceability >= 0 AND danceability <= 1),
    energy DECIMAL(16,15) CHECK (energy >= 0 AND energy <= 1),
    music_key INT,
    loudness DECIMAL(17,15),
    mode INT NOT NULL, CHECK (mode IN (0, 1)),
    speechiness DECIMAL(16,15) CHECK (speechiness >= 0 AND speechiness <= 1),
    acousticness DECIMAL(16,15) CHECK (acousticness >= 0 AND acousticness <= 1),
    instrumentalness DECIMAL(16,15) CHECK (instrumentalness >= 0 AND instrumentalness <= 1),
    liveness DECIMAL(16,15) CHECK (liveness >= 0 AND liveness <= 1),
    valence DECIMAL(16,15) CHECK (valence >= 0 AND valence <= 1),
    tempo DECIMAL(7, 3),
    time_signature INT,
    track_genre VARCHAR(30),
    PRIMARY KEY (track_id)
);

CREATE TABLE IF NOT EXISTS Track_Artists (
    track_id VARCHAR(30) NOT NULL, 
    artist VARCHAR(254) NOT NULL,
    PRIMARY KEY (track_id, artist),
    FOREIGN KEY (track_id) REFERENCES Tracks(track_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Playlist_Tracks (
    username  VARCHAR(36) NOT NULL,
    playlist_name VARCHAR(36) NOT NULL,
    track_id VARCHAR(30) NOT NULL, 
    PRIMARY KEY (username, playlist_name, track_id),
    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE,
	FOREIGN KEY (username, playlist_name) REFERENCES Playlists(username, playlist_name) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (track_id) REFERENCES Tracks(track_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Playlist_Likes (
    owner_username VARCHAR(36) NOT NULL, 
    playlist_name VARCHAR(36) NOT NULL, 
    liked_username VARCHAR(36) NOT NULL,
    PRIMARY KEY (owner_username, playlist_name, liked_username),
    FOREIGN KEY (owner_username) REFERENCES Users(username) ON DELETE CASCADE,
	FOREIGN KEY (owner_username, playlist_name) REFERENCES Playlists(username, playlist_name) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (liked_username) REFERENCES Users(username) ON DELETE CASCADE
);