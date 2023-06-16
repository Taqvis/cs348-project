-- Feature 1: Search song by Track name (selects both track and artists)
SELECT * FROM Tracks WHERE track_name LIKE “%INPUT%”;
SELECT * FROM Track_Artists WHERE EXISTS (SELECT track_id FROM Tracks WHERE Tracks.track_id = Track_Artists.track_id AND Tracks.track_name LIKE “%INPUT%”);

-- Feature 2: Rename a playlist
UPDATE Playlists SET playlist_name=NEW_NAME WHERE playlist_name=OLD_NAME  AND username=USERNAME AND EXISTS (SELECT Playlists WHERE playlist_name=NEW_NAME AND username=USERNAME);

-- Feature 3: Delete a song from the playlist
DELETE FROM Playlist_Songs WHERE username=USERNAME AND playlist_name=PLAYLIST_NAME AND track_id=TRACK_ID;

-- Feature 4: Create a playlist
INSERT INTO Playlists (username, playlist_name) VALUES (USERNAME, PLAYLIST_NAME);
