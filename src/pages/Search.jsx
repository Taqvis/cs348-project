import React, { useEffect, useState } from "react";
import axios from "axios";
import { connect } from "react-redux";

function Search(props) {
  const { username, password } = props;
  const [searchQuery, setSearchQuery] = useState("");
  const [displayedSongs, setDisplayedSongs] = useState(null);
  const [displayedPlaylists, setDisplayedPlaylists] = useState(null);

  const auth = 'Basic ' + btoa(username + ':' + password);

  // get the users playlists
  useEffect(() => {
    getPlaylists(auth, username, setDisplayedPlaylists);
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Searching for:", searchQuery);

    searchSongs(auth, searchQuery, setDisplayedSongs);

    setSearchQuery("");
  };

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
    <div className="bg-slate-600 min:h-screen px-10 py-10 w-full">
      <form className="flex justify-center" onSubmit={handleSubmit}>
        <div className="w-1/2">
          <div className="flex space-x-4">
            <div className="flex rounded-2xl overflow-hidden w-full">
              <input
                type="text"
                className="w-full text-black rounded-md rounded-r-none px-4"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
              <button className="bg-blue-500 hover:bg-blue-700 text-black px-6 text-lg font-semibold py-4 rounded-r-md">
                Go
              </button>
            </div>
          </div>
          <div className="flex space-x-1 items-center mb-2">
            <p className="text-black text-lg font-semibold">
              Please enter something
            </p>
          </div>
        </div>
      </form>
      <div className="flex justify-center flex-col bg-slate-600">
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
          <h1 className="text-black">No Songs Found</h1>
        )}
      </div>
    </div>
  );
}

async function searchSongs(auth, searchQuery, hook) {
  console.log("Searching for:", searchQuery);
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

export default connect(mapStateToProps)(Search);
