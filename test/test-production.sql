DELIMITER ;

SET SESSION profiling = 1;
SET SESSION profiling_history_size = 3;

-- Delete all values in every non-track table
DELETE FROM Playlist_Tracks;
DELETE FROM Playlists;
DELETE FROM Users;

-- Feature 3.i: Create account
INSERT INTO Users VALUES ("testuser", "Test User", "testpass", NULL);

SELECT * FROM Users WHERE username = "testuser";

-- Feature 3.ii: Login
SELECT COUNT(*) FROM Users WHERE username="testuser" AND password="testpass";

-- Feature 2.i: Create a playlist
INSERT INTO Playlists VALUES ("testuser", "Test Playlist");

SELECT * FROM Playlists WHERE username = "testuser" AND playlist_name = "Test Playlist";

-- Feature 2.ii: Add song to playlist
INSERT INTO Playlist_Tracks VALUES ("testuser", "Test Playlist", "02shCNmb6IvgB5jLqKjtkK");
INSERT INTO Playlist_Tracks VALUES ("testuser", "Test Playlist", "4GroumsjzAcgCwJzTGY7px");

-- Feature 2.iii: View songs in a playlist
SELECT * FROM Playlist_Tracks 
	NATURAL JOIN Tracks 
	NATURAL JOIN Track_Artists 
WHERE username = "testuser" 
	AND playlist_name = "Test Playlist";

-- Feature 2.iv: Rename a playlist
UPDATE Playlists 
SET playlist_name="New Test Playlist" 
WHERE playlist_name="Test Playlist" 
	AND username="testuser";

SELECT * FROM Playlists NATURAL JOIN Playlist_Tracks WHERE username = "testuser";

-- Feature 2.v: Delete a song from the playlist
DELETE FROM Playlist_Tracks
WHERE username="testuser" 
	AND playlist_name="New Test Playlist" 
	AND track_id="4GroumsjzAcgCwJzTGY7px";

SELECT track_id, track_name 
FROM Playlist_Tracks 
	NATURAL JOIN Tracks 
WHERE username = "testuser" 
	AND playlist_name = "New Test Playlist";


-- Feature 2.vi: Like a playlist
-- Add another playlist and another user
INSERT INTO Users VALUES ("testuser2", "Test User 2", "testpass2", NULL);
INSERT INTO Playlists VALUES ("testuser2", "Another Playlist");

-- Test User likes Test User 2's "Another Playlist"
INSERT INTO Playlist_Likes VALUES ("testuser2", "Another Playlist", "testuser");
INSERT INTO Playlist_Tracks VALUES ("testuser2", "Another Playlist", "02shCNmb6IvgB5jLqKjtkK");
INSERT INTO Playlist_Tracks VALUES ("testuser2", "Another Playlist", "4GroumsjzAcgCwJzTGY7px");

SELECT * FROM Playlist_Likes WHERE liked_username="testuser";


-- Feature 1.i: Search song by track name and artist name

-- Remove if first run
ALTER TABLE Track_Artists
DROP INDEX artistsByTrackId;

-- b: without index
EXPLAIN SELECT * FROM Tracks
	NATURAL JOIN Track_Artists 
WHERE Tracks.track_name LIKE "%a%" 
	OR Track_Artists.artist LIKE "%a%"
ORDER BY popularity DESC;

-- c: with index
CREATE INDEX artistsByTrackId
ON Track_Artists(track_id);

EXPLAIN SELECT * FROM Tracks
	NATURAL JOIN Track_Artists 
WHERE Tracks.track_name LIKE "%a%" 
	OR Track_Artists.artist LIKE "%a%"
ORDER BY popularity DESC;

SHOW PROFILES;

-- Feature 1.ii: Search playlist by name
SELECT * FROM Playlists WHERE playlist_name LIKE "%New Test Playlist%";

-- Feature 4: Song Recommendations

-- 4.ii: Danceability recommendations

-- b: without index
EXPLAIN WITH liked_avg AS (
	SELECT AVG(danceability) AS averageVal FROM Tracks
	WHERE track_id IN 
		(SELECT track_id FROM LikedPlaylist 
		WHERE liked_username = "testuser"))
SELECT * FROM Tracks, liked_avg 
WHERE ABS((Tracks.danceability - liked_avg.averageVal)) <= 0.05
LIMIT 10;

-- c: with index
CREATE INDEX tracksByDanceability
ON Tracks(danceability);

EXPLAIN WITH liked_avg AS (
	SELECT AVG(danceability) AS averageVal FROM Tracks
	WHERE track_id IN 
		(SELECT track_id FROM LikedPlaylist 
		WHERE liked_username = "testuser"))
SELECT * FROM Tracks, liked_avg 
WHERE ABS((Tracks.danceability - liked_avg.averageVal)) <= 0.05
LIMIT 10;

SHOW PROFILES;

-- Feature 6: Artist Page


-- Feature 6.i: Display list of albums

ALTER TABLE Track_Artists
DROP INDEX artistsByName;

-- b: without index
EXPLAIN SELECT DISTINCT album_name FROM Tracks 
	INNER JOIN Track_Artists as Artists 
	ON Tracks.track_id = Artists.track_id 
WHERE Artists.artist = "Linkin Park";

-- c: with index
CREATE INDEX artistsByName
ON Track_Artists(artist);

EXPLAIN SELECT DISTINCT album_name FROM Tracks 
	INNER JOIN Track_Artists as Artists 
	ON Tracks.track_id = Artists.track_id 
WHERE Artists.artist = "Linkin Park";

SHOW PROFILES;

-- Feature 6.ii: Display average popularity
SELECT AVG(popularity) 
FROM Tracks 
	INNER JOIN Track_Artists as Artists 
		ON Tracks.track_id = Artists.track_id 
GROUP BY Artists.artist HAVING Artists.artist = "Linkin Park";

-- Feature 6.iii: Display most appeared genre(s)
SELECT Tracks.track_genre FROM Tracks 
	INNER JOIN Track_Artists as Artists 
		ON Tracks.track_id = Artists.track_id 
GROUP BY Artists.artist, Tracks.track_genre 
HAVING Artists.artist = "Linkin Park"
ORDER BY COUNT(*) DESC, Tracks.track_genre ASC 
LIMIT 2;

-- Feature 6.iv: Display total likes
-- Add two songs to a liked playlist first

INSERT INTO Playlist_Tracks 
VALUES ("testuser2", "Another Playlist", "1B8WdDScvobpFsZLfdmIE1");
INSERT INTO Playlist_Tracks 
VALUES ("testuser2", "Another Playlist", "5AMWj2Dc7oNb4qCHb5cOad");


SELECT COUNT(*) 
FROM Playlist_Tracks AS pt 
	INNER JOIN Playlist_Likes AS pl 
		ON pt.playlist_name = pl.playlist_name 
			AND pt.username = pl.owner_username
	INNER JOIN Track_Artists ta 
		ON ta.track_id = pt.track_id
GROUP BY ta.artist 
HAVING ta.artist = "Linkin Park";


-- Feature 2vi: Delete a playlist
DELETE FROM Playlists 
WHERE username="testuser" 
	AND playlist_name="New Test Playlist";

SELECT * FROM Playlists WHERE username = "testuser";

-- Feature 2viii: Remove a like on a playlist
DELETE FROM Playlist_Likes 
WHERE owner_username="testuser2"
	AND playlist_name="Another Playlist"
	AND liked_username="testuser";

SELECT * FROM Playlist_Likes 
WHERE liked_username="testuser";

-- Create 7 users for leaderboard
INSERT INTO Users VALUES ("testuser1", "Test User 1", "testpass", NULL);
INSERT INTO Users VALUES ("testuser3", "Test User 3", "testpass", NULL);
INSERT INTO Users VALUES ("testuser4", "Test User 4", "testpass", NULL);
INSERT INTO Users VALUES ("testuser5", "Test User 5", "testpass", NULL);
INSERT INTO Users VALUES ("testuser6", "Test User 6", "testpass", NULL);
INSERT INTO Users VALUES ("testuser7", "Test User 7", "testpass", NULL);

-- Add playlists
INSERT INTO Playlists VALUES ("testuser1", "Test Playlist 1");
INSERT INTO Playlists VALUES ("testuser2", "Test Playlist 2");
INSERT INTO Playlists VALUES ("testuser3", "Test Playlist 3");
INSERT INTO Playlists VALUES ("testuser4", "Test Playlist 4");
INSERT INTO Playlists VALUES ("testuser5", "Test Playlist 5");
INSERT INTO Playlists VALUES ("testuser6", "Test Playlist 6");
INSERT INTO Playlists VALUES ("testuser7", "Test Playlist 7");

-- Add likes to adjust leaderboard rank
INSERT INTO Playlist_Likes(owner_username, playlist_name, liked_username) 
	VALUES ("testuser1", "Test Playlist 1", "testuser3"),
		("testuser1", "Test Playlist 1", "testuser4"),
		("testuser1", "Test Playlist 1", "testuser5"),
		("testuser1", "Test Playlist 1", "testuser6"),
		("testuser1", "Test Playlist 1", "testuser7");

INSERT INTO Playlist_Likes(owner_username, playlist_name, liked_username) 
	VALUES ("testuser2", "Test Playlist 2", "testuser3"), 
		("testuser2", "Test Playlist 2", "testuser4"),
		("testuser2", "Test Playlist 2", "testuser5"),
		("testuser2", "Test Playlist 2", "testuser6");

-- No problem with someone liking their own playlist
INSERT INTO Playlist_Likes(owner_username, playlist_name, liked_username) 
	VALUES ("testuser3", "Test Playlist 3", "testuser3"), 
		("testuser3", "Test Playlist 3", "testuser4"),
		("testuser3", "Test Playlist 3", "testuser5"),
		("testuser3", "Test Playlist 3", "testuser6");

INSERT INTO Playlist_Likes(owner_username, playlist_name, liked_username) 
	VALUES ("testuser4", "Test Playlist 4", "testuser3"), 
		("testuser4", "Test Playlist 4", "testuser4");

INSERT INTO Playlist_Likes(owner_username, playlist_name, liked_username) 
	VALUES ("testuser5", "Test Playlist 5", "testuser5");

-- View leaderboard
SELECT * FROM leaderboard LIMIT 10;

-- Feature 5.ii: Update tier on like/unlike
SELECT tier FROM Users WHERE username = "testuser2";

-- testuser1 likes testuser2's playlist
INSERT INTO Playlist_Likes VALUES ("testuser2", "Test Playlist 2", "testuser1");

SELECT * FROM leaderboard LIMIT 10;
SELECT tier FROM Users WHERE username = "testuser2";

-- testuser1 unlikes testuser2's playlist
DELETE FROM Playlist_Likes 
WHERE owner_username="testuser2"
	AND playlist_name="Test Playlist 2"
	AND liked_username="testuser1"; 

SELECT * FROM leaderboard LIMIT 10;
SELECT tier FROM Users WHERE username = "testuser2";
SELECT tier FROM Users;