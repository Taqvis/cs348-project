CREATE TABLE IF NOT EXISTS Users (
	username VARCHAR(36) NOT NULL,
    display_name VARCHAR(36) NOT NULL,
    password VARCHAR(20) NOT NULL CHECK (LENGTH(password) >= 8),
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS Playlists (
	username VARCHAR(36) NOT NULL,
    playlist_name VARCHAR(36) NOT NULL,
    PRIMARY KEY (username, playlist_name),
    CONSTRAINT FK_Playlists_Users FOREIGN KEY (username) 
    REFERENCES Users(username)
);

CREATE TABLE IF NOT EXISTS Tracks (
    track_id VARCHAR(30) NOT NULL,
    album_name VARCHAR(254) NOT NULL,
    track_name VARCHAR(254) NOT NULL,
    popularity NOT NULL INT CHECK (0 <= popularity AND popularity <= 100),
    duration_ms INT,
    explicit BOOLEAN,
    danceability DECIMAL(1,15) CHECK (0 <= danceability AND danceability <= 1),
    energy DECIMAL(1,15),
    key INT,
    loudness DECIMAL(1,15),
    mode INT NOT NULL CHECK (mode IN (0, 1)),
    speechiness DECIMAL(1,15) CHECK (0 <= speechiness AND speechiness <= 1),
    acousticness DECIMAL(1,15) CHECK (0 <= acousticness AND acousticness <= 1),
    instrumentalness DECIMAL(1,15) CHECK (0 <= instrumentalness AND instrumentalness <= 1),
    liveness DECIMAL(1,15) CHECK (0 <= liveness AND liveness <= 1),
    valence DECIMAL(1,15) CHECK (0 <= valence AND valence <= 1),
    tempo DECIMAL(15, 15),
    time_signature INT,
    track_genre VARCHAR(30),
    PRIMARY KEY (track_id)
);

CREATE TABLE IF NOT EXISTS Track_Artists (
    track_id VARCHAR(30) NOT NULL, 
    artist VARCHAR(254) NOT NULL,
    PRIMARY KEY (track_id, artist),
    CONSTRAINT FK_track_artists_tracks FOREIGN KEY (track_id)
    REFERENCES Tracks(track_id)
);

CREATE TABLE IF NOT EXISTS Playlist_Songs (
    username  VARCHAR(36) NOT NULL,
    playlist_name VARCHAR(36) NOT NULL,
    track_id VARCHAR(30) NOT NULL, 
    PRIMARY KEY (username, playlist_name, track_id),
    CONSTRAINT FK_playlist_songs_playlists FOREIGN KEY (username, playlist_name)
    REFERENCES Playlists(username, playlist_name),
    CONSTRAINT FK_playlist_songs_tracks FOREIGN KEY (track_id)
    REFERENCES Tracks(track_id)
);

CREATE TABLE IF NOT EXISTS Playlist_Members (
    owner_username VARCHAR(36) NOT NULL REFERENCES Users(username),
    playlist_name VARCHAR(36) NOT NULL,
    member_username VARCHAR(36) NOT NULL REFERENCES Users(username),
    PRIMARY KEY (owner_username, playlist_name, member_username),
    CONSTRAINT FK_playlist_members_playlists FOREIGN KEY (playlist_name)
    REFERENCES Playlists(playlist_name),
);

CREATE TABLE IF NOT EXISTS Playlist_Likes (
    owner_username VARCHAR(36) NOT NULL REFERENCES Users(username),
    playlist_name VARCHAR(36) NOT NULL,
    member_username VARCHAR(36) NOT NULL REFERENCES Users(username),
    PRIMARY KEY (owner_username, playlist_name, member_username),
    CONSTRAINT FK_playlist_likes_playlists FOREIGN KEY (playlist_name)
    REFERENCES Playlists(playlist_name)
);