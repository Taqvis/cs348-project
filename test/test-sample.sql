-- Delete all values in every non-track table
DELETE FROM Playlist_Songs;
DELETE FROM Playlists;
DELETE FROM Users;

-- Feature 6: Create account
INSERT INTO Users VALUES ("testuser", "Test User", "testpass");

SELECT * FROM Users WHERE username = "testuser";

-- Feature 7: Login
SELECT COUNT(*) FROM Users WHERE username="testuser" AND password="testpass";

-- Feature 1: Search song by Track name
SELECT * FROM Tracks WHERE track_name LIKE "%Nevada%";

SELECT * FROM Track_Artists WHERE EXISTS (SELECT track_id FROM Tracks WHERE Tracks.track_id = Track_Artists.track_id AND Tracks.track_name LIKE "%Nevada%");

-- Feature 2: Create a playlist
INSERT INTO Playlists VALUES ("testuser", "Test Playlist");

SELECT * FROM Playlists WHERE username = "testuser" AND playlist_name = "Test Playlist";

-- Feature 3: Rename a playlist
UPDATE Playlists SET playlist_name="New Test Playlist" WHERE playlist_name="Test Playlist"  AND username="testuser";

SELECT * FROM Playlists WHERE username = "testuser";

-- Feature 4: Add song to playlist
INSERT INTO Playlist_Songs VALUES ("testuser", "New Test Playlist", "02shCNmb6IvgB5jLqKjtkK");
INSERT INTO Playlist_Songs VALUES ("testuser", "New Test Playlist", "4GroumsjzAcgCwJzTGY7px");

-- Feature 5: Delete a song from the playlist
DELETE FROM Playlist_Songs WHERE username="testuser" AND playlist_name="New Test Playlist" AND track_id="4GroumsjzAcgCwJzTGY7px";

SELECT track_id, track_name FROM Playlist_Songs NATURAL JOIN Tracks WHERE username = "testuser" AND playlist_name = "New Test Playlist";