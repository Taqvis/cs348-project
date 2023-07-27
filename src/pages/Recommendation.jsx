import React, { useEffect, useState } from "react";
import axios from "axios";
import { connect } from "react-redux";

function Recommendation(props) {
  const { username, password } = props;
  const [displayedSongs, setDisplayedSongs] = useState(null);
  const [displayedPlaylists, setDisplayedPlaylists] = useState(null);
  const [recommendationType, setRecommendationType] = useState("energy");

  const auth = 'Basic ' + btoa(username + ':' + password);

  // get the users playlists
  useEffect(() => {
    getPlaylists(auth, username, setDisplayedPlaylists);
    getRecommendations(auth, recommendationType, username, setDisplayedSongs);
  }, [auth, username, recommendationType]);

  const handleAddToPlaylist = (song, playlist) => {
    if (playlist === "") {
      console.log("No playlist selected");
    } else {
      console.log("Adding to playlist:", song);
      console.log("Playlist:", playlist);
      addSongToPlaylist(auth, username, playlist, song.trackId);
    }
    
  };
  return (
    <div className="bg-slate-600 h-screen px-10 py-10 w-full">
      <div className="flex justify-center flex-row">
        <div className="flex text-black text-lg m-3 p-3 bg-slate-500 rounded-lg" onClick={() => setRecommendationType("danceability")}>
          Danceability
        </div>
        <div className="flex text-black text-lg m-3 p-3 bg-slate-500 rounded-lg" onClick={() => setRecommendationType("energy")}>
          Energy
        </div>
        <div className="flex text-black text-lg m-3 p-3 bg-slate-500 rounded-lg" onClick={() => setRecommendationType("tempo")}>
          Tempo
        </div>
      </div>
      <h1 className="flex justify-center text-2xl font-bold my-1 py-3 rounded-lg">
      Here are your {recommendationType} recommendations:
      </h1>
      <div className="flex justify-center flex-col">
        {displayedSongs ? (
          <div className="justify-center space-y-2">
            {displayedSongs ? (
              displayedSongs.map((song) => {
                return (
                  <div className="flex flex-row text-black text-lg p-3 bg-slate-500 rounded-lg">
                    {song.trackName}
                    <select
                      className="ml-auto"
                      onChange={(e) =>
                        handleAddToPlaylist(song, e.target.value)
                      }
                    >
                      <option value="">Add to Playlist</option>;
                      {displayedPlaylists ? (
                        console.log(displayedPlaylists),
                        displayedPlaylists.map((playlist) => {
                          return (
                            <option value={playlist.playlistName}>
                              {playlist.playlistName}
                            </option>
                          );
                        })
                      ) : (
                        <option disabled>no playlists found</option>
                      )}
                    </select>
                  </div>
                );
              })
            ) : (
              <h1 className="text-black">No Playlists Found</h1>
            )}
          </div>
        ) : (
          <h1 className="text-black">No Recommendations, Like a playlist to get recommendations</h1>
        )}
      </div>
    </div>
  );
}

async function searchSongs(auth, searchQuery, hook) {
  console.log("Searching for:", searchQuery);
  // GET /tracks/{name} - get tracks by name
  if (!searchQuery) {
    console.log("No search query provided");
    return;
  } else {
    try {
      const tracks = await axios.get(
        `http://localhost:8080/tracks/${searchQuery}`,
        {
          headers: {
            Authorization: auth,
          },
        }
      );
      hook(tracks.data);
      console.log("Songs found:", tracks.data);
    } catch (error) {
      console.error("Error occurred while searching:", error);
    }
  }
}

async function getRecommendations(auth, type, username, hook) {
  try {
    const response = await axios.get(
      `http://localhost:8080/recommend/${type}/${username}`,
      {
        headers: {
          Authorization: auth,
        },
      }
    );
    console.log("response:", response);
    hook(response.data);
  } catch (error) {
    console.error("Error occurred while getting reccomendations:", error);
  }
}

async function getPlaylists(auth, username, hook) {
  try {
    const response = await axios.get(
      `http://localhost:8080/user/playlist/${username}`,
      {
        headers: {
          Authorization: auth,
        },
      }
    );
    console.log("response:", response);
    hook(response.data);
  } catch (error) {
    console.error("Error occurred while getting playlists:", error);
  }
}

async function addSongToPlaylist(auth, username, playlistName, trackName) {
  console.log("Adding song to playlist:", trackName);
  console.log("Playlist:", playlistName);
  console.log("Username:", username);
  console.log("Auth:", auth);
  try {
    const response = await axios.post(
      `http://localhost:8080/playlist/${username}/${playlistName}/${trackName}`,
      {},
      {
        headers: {
          Authorization: auth,
        },
      }
    );
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while adding song:", error);
  }  
}


const mapStateToProps = (state) => ({
  username: state.username,
  password: state.password,
});

export default connect(mapStateToProps)(Recommendation);
