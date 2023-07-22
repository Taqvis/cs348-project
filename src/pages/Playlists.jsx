import React, { useEffect, useState } from "react";
import axios from "axios";
import PlaylistPopup from "../components/PlaylistPopup";
import { connect } from "react-redux";

function Playlists({ username }) {
  const [showPlaylistPopup, setShowPlaylistPopup] = useState(false);
  const [showRenamePopup, setShowRenamePopup] = useState(false);

  const [searchQuery, setSearchQuery] = useState("");
  const [currentPlaylist, setCurrentPlaylist] = useState(null);

  const [playlists, setPlaylists] = useState(null);
  const [foundPlaylists, setFoundPlaylists] = useState(null);

  useEffect(() => {
    if (!username) {
      console.log("No username provided");
      return;
    } else {
      getPlaylists(username, setPlaylists);
      console.log("Playlists:", playlists);
    }
  }, [username, showPlaylistPopup]);

  const handleSearch = (e) => {
    e.preventDefault();
    console.log("Searching for:", searchQuery);
    searchPlaylist(searchQuery, setFoundPlaylists);
  };

  const handlePlaylistClick = (e, playlist) => {
    console.log(playlist);
    console.log(e.target.innerHTML);

    if (e.target.innerHTML === "Delete") {
      deletePlaylist(playlist.username, playlist.playlistName);
    } else if (e.target.innerHTML === "Rename") {
      // renamePlaylist(playlist.username, playlist.playlistName);
      setShowRenamePopup(true);
    } else {
      setCurrentPlaylist(playlist);
    }
  };

  const handleRenamePlaylist = (playlistName) => {
    console.log("Renaming Playlist:", playlistName);
    // console.log("FEATURE NOT IMPLEMENTED");
    // renamePlaylist(username, currentPlaylist.playlistName, playlistName);
  };

  const handleRemoveItemFromPlaylist = (e, item) => {
    console.log("Delete clicked:", item);
    removeSongFromPlaylist(
      username,
      currentPlaylist.playlistName,
      item.trackId
    );
  };

  const handleLikePlaylist = (e, playlist) => {
    likePlaylist(username, playlist.username, playlist.playlistName);
  };

  const handleUnlikePlaylist = (e, playlist) => {
    unlikePlaylist(username, playlist.username, playlist.playlistName);
  };

  const handleCreatePlaylist = async (playlistName) => {
    console.log("Creating Playlist:", playlistName);

    createPlaylist(username, playlistName);
  };

  function hasLikedUsername(playlistData) {
    if (playlistData.likes) {
      return playlistData.likes.some(like => like.likedUsername === username);
    }
    return false;
  }
  
  return (
    <>
      {showPlaylistPopup && (
        <PlaylistPopup
          onClose={() => {
            setShowPlaylistPopup(false);
          }}
          onConfirm={handleCreatePlaylist}
        />
      )}
      {showRenamePopup && (
        <PlaylistPopup
          onClose={() => {
            setShowRenamePopup(false);
          }}
          onConfirm={handleRenamePlaylist}
        />
      )}

      <div className="bg-slate-600 h-screen px-10 py-10 w-full flex flex-row">
        <div className="flex flex-col w-1/4 min-h-full">
          {currentPlaylist ? (
            <div
              className="flex justify-center text-black text-2xl py-3 bg-blue-500 hover:bg-blue-700 rounded-lg"
              onClick={() => {
                setCurrentPlaylist(null);
              }}
            >
              Search
            </div>
          ) : (
            <></>
          )}

          <h1 className="flex justify-center text-2xl font-bold my-1 py-3 rounded-lg">
            Your Playlists
          </h1>

          {playlists ? (
            playlists.map((item) => {
              return (
                <div
                  className="text-black text-xl mt-3 px-5 py-1 hover:bg-slate-700 bg-slate-500 rounded-lg"
                  onClick={(e) => handlePlaylistClick(e, item)}
                >
                  {item.playlistName}
                  <div className="flex flex-row">
                    <div className="text-sm pt-1 hover:bg-purple-500 w-fit rounded-lg text-purple-300">
                      Rename
                    </div>
                    <div className="ml-auto text-sm p-1 hover:bg-red-500 w-fit rounded-lg text-red-300">
                      Delete
                    </div>
                  </div>
                </div>
              );
            })
          ) : (
            <h1 className="flex justify-center">No Playlists Found</h1>
          )}
          <div
            className="mt-auto text-2xl py-3 px-5 hover:bg-slate-700 bg-slate-500 rounded-lg w-fit"
            onClick={() => {
              setShowPlaylistPopup(true);
            }}
          >
            New Playlist
          </div>
        </div>
        <div className="flex justify-center grow">
          <div className="flex flex-col grow space-y-3 mx-10">
            {currentPlaylist ? (
              currentPlaylist.tracks.map((track) => {
                return (
                  <div className="flex flex-row text-black text-lg p-3 bg-slate-500 rounded-lg">
                    {/* <h1>{track.trackName}</h1> */}
                    <h1>{track.trackId}</h1>
                    <button
                      className="ml-auto px-3 hover:bg-red-700 rounded-lg"
                      onClick={(e) => handleRemoveItemFromPlaylist(e, track)}
                    >
                      Remove
                    </button>
                  </div>
                );
              })
            ) : (
              <>
                <form className="justify-center" onSubmit={handleSearch}>
                  <div className="w-full">
                    <div className="flex space-x-4">
                      <div className="flex rounded-2xl overflow-hidden w-full">
                        <input
                          type="text"
                          className="w-full text-black rounded-md rounded-r-none px-4"
                          placeholder="Search for a playlist"
                          onChange={(e) => setSearchQuery(e.target.value)}
                          value={searchQuery}
                        />
                        <button className="bg-blue-500 hover:bg-blue-700 text-black px-6 text-lg font-semibold py-4 rounded-r-md">
                          Go
                        </button>
                      </div>
                    </div>
                  </div>
                </form>
                <div className="justify-center space-y-2">
                  {foundPlaylists ? (
                    foundPlaylists.map((playlist) => {
                      return (
                        <div className="flex flex-row text-black text-lg p-3 bg-slate-500 rounded-lg">
                          {playlist.playlistName}
                          {hasLikedUsername(playlist) ? (
                            <button
                              className="ml-auto px-3 hover:bg-red-700 rounded-lg"
                              onClick={(e) => handleUnlikePlaylist(e, playlist)}
                            >
                              Unlike
                            </button>
                          ) : (
                            <button
                              className="ml-auto px-3 hover:bg-green-700 rounded-lg"
                              onClick={(e) => handleLikePlaylist(e, playlist)}
                            >
                              Like
                            </button>
                          )}
                        </div>
                      );
                    })
                  ) : (
                    <h1 className="text-black">No Playlists Found</h1>
                  )}
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
}

async function searchPlaylist(searchQuery, hook) {
  try {
    const response = await axios.get(
      `http://localhost:8080/playlist/${searchQuery}`
    );
    hook(response.data);
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while searching:", error);
  }
}

async function createPlaylist(username, playlistName) {
  try {
    const response = await axios.post(
      `http://localhost:8080/playlist/${username}/${playlistName}`
    );
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while creating:", error);
  }
}

// get all playlists of user
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

async function deletePlaylist(username, playlistName) {
  try {
    const response = await axios.delete(
      `http://localhost:8080/playlist/${username}/${playlistName}`
    );
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while deleting:", error);
  }
}

async function removeSongFromPlaylist(username, playlistName, trackId) {
  try {
    const response = await axios.delete(
      `http://localhost:8080/playlist/${username}/${playlistName}/${trackId}`
    );
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while removing:", error);
  }
}

async function likePlaylist(username, ownerName, playlistName) {
  try {
    const response = await axios.post(
      `http://localhost:8080/like/${ownerName}/${playlistName}/${username}`
    );
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while liking:", error);
  }
}

async function unlikePlaylist(username, ownerName, playlistName) {
  try {
    const response = await axios.delete(
      `http://localhost:8080/like/${ownerName}/${playlistName}/${username}`
    );
    console.log("response:", response);
  } catch (error) {
    console.error("Error occurred while unliking:", error);
  }
}

const mapStateToProps = (state) => ({
  username: state.username,
});

export default connect(mapStateToProps)(Playlists);
