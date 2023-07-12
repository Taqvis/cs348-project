import React, { useEffect, useState } from "react";
import axios from "axios";
import { connect } from "react-redux";

function Search({ username }) {
  const [searchQuery, setSearchQuery] = useState("");
  const [displayedSongs, setDisplayedSongs] = useState(null);
  const [displayedPlaylists, setDisplayedPlaylists] = useState(null);

  // get the users playlists
  useEffect(() => {
    getPlaylists(username, setDisplayedPlaylists);
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Searching for:", searchQuery);

    searchSongs(searchQuery, setDisplayedSongs);

    setSearchQuery("");
  };

  const handleAddToPlaylist = (song, playlist) => {
    if (playlist === "") {
      console.log("No playlist selected");
    } else {
      console.log("Adding to playlist:", song);
      console.log("Playlist:", playlist);
      addSongToPlaylist(username, playlist, song.trackId);
    }

  };
  return (
    <div class="bg-slate-600 h-screen px-10 py-10 w-full">
      <form class="flex justify-center" onSubmit={handleSubmit}>
        <div class="w-1/2">
          <div class="flex space-x-4">
            <div class="flex rounded-2xl overflow-hidden w-full">
              <input
                type="text"
                class="w-full text-black rounded-md rounded-r-none px-4"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
              <button class="bg-blue-500 hover:bg-blue-700 text-black px-6 text-lg font-semibold py-4 rounded-r-md">
                Go
              </button>
            </div>
          </div>
          <div class="flex space-x-1 items-center mb-2">
            <p class="text-black text-lg font-semibold">
              Please enter something
            </p>
          </div>
        </div>
      </form>
      <div class="flex justify-center flex-col">
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
              <h1 class="text-black">No Playlists Found</h1>
            )}
          </div>
        ) : (
          <h1 class="text-black">No Songs Found</h1>
        )}
      </div>
    </div>
  );
}

async function searchSongs(searchQuery, hook) {
  console.log("Searching for:", searchQuery);
  // GET /tracks/{name} - get tracks by name
  if (!searchQuery) {
    console.log("No search query provided");
    return;
  } else {
    try {
      const tracks = await axios.get(
        `http://localhost:8080/tracks/${searchQuery}`
      );
      hook(tracks.data);
      console.log("Songs found:", tracks.data);
    } catch (error) {
      console.error("Error occurred while searching:", error);
    }
  }
}

async function getPlaylists(username, hook) {
  try {
    const response = await axios.get(
      `http://localhost:8080/user/playlist/${username}`
    );
    console.log("response:", response);
    hook(response.data);
  } catch (error) {
    console.error("Error occurred while getting playlists:", error);
  }
}

async function addSongToPlaylist(username, playlistName, trackName) {
  try {
    const response = await axios.post(
      `http://localhost:8080/playlist/${username}/${playlistName}/${trackName}`
    );
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while adding song:", error);
  }  
}


const mapStateToProps = (state) => ({
  username: state.username,
});

export default connect(mapStateToProps)(Search);
