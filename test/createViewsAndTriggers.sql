-- Feature 4.i: LikedTracks View
CREATE VIEW LikedTracks AS
	SELECT t.track_id AS track_id,
		t.track_name AS track_name, 
		l.liked_username AS liked_username
	FROM Tracks AS t
		INNER JOIN Playlist_Tracks AS pt
			ON t.track_id = pt.track_id
		INNER JOIN Playlist_Likes AS l
			ON pt.username = l.owner_username
			AND pt.playlist_name = l.playlist_name;

-- Feature 5.i: Leaderboard table view
CREATE VIEW leaderboard AS
	SELECT userLikes.owner_username AS owner_username, 
		userLikes.likes AS likes, 
		RANK() OVER(ORDER BY likes DESC) AS position
	FROM (SELECT l.owner_username AS owner_username,
			MAX(l.likes) AS likes
		FROM (SELECT owner_username, 
				playlist_name, 
				COUNT(*) AS likes
			FROM Playlist_Likes
			GROUP BY owner_username, playlist_name) AS l
		GROUP BY owner_username) AS userLikes
	ORDER BY position;

-- Create triggers
-- Feature 5.ii: Updating User tiers
DELIMITER //
CREATE TRIGGER changeTiersOnInsert
AFTER INSERT ON Playlist_Likes
FOR EACH ROW
BEGIN
	SELECT position
		FROM leaderboard AS l
		WHERE l.owner_username = NEW.owner_username INTO @pos;
	UPDATE Users
	SET tier = CASE
		WHEN @pos > 50 THEN NULL
		WHEN @pos <= 50 AND @pos > 25 THEN 0
		WHEN @pos <= 25 AND @pos > 5 THEN 1
		WHEN @pos <= 5 AND @pos > 1 THEN 2
		ELSE 3
	END
	WHERE username = NEW.owner_username;
END;
//

DELIMITER //
CREATE TRIGGER changeTiersOnDelete
AFTER DELETE ON Playlist_Likes
FOR EACH ROW
BEGIN
	SELECT position
		FROM leaderboard AS l
		WHERE l.owner_username = OLD.owner_username INTO @pos;
	UPDATE Users
	SET tier = CASE
		WHEN @pos > 50 THEN NULL
		WHEN @pos <= 50 AND @pos > 25 THEN 0
		WHEN @pos <= 25 AND @pos > 5 THEN 1
		WHEN @pos <= 5 AND @pos > 1 THEN 2
    	ELSE 3
	END
	WHERE username = OLD.owner_username;
END;
//

DELIMITER ;
