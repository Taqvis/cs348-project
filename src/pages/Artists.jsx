import React, { useEffect, useState } from "react";
import axios from "axios";
import { connect } from "react-redux";

function Artists({ artist }) {
  const [searchQuery, setSearchQuery] = useState("");
  const [allArtists, setAllArtists] = useState(null);
  const [searchedArtist, setSearchedArtist] = useState([]);

  // get the users playlists
  useEffect(() => {
    getAllArtists(setAllArtists);
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Searching for:", searchQuery);
    searchArtists(searchQuery, setSearchedArtist, setAllArtists);
    setSearchQuery("");
  };

  return (
    <div className="bg-slate-600 h-screen px-10 py-10 w-full">
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
      <div className="flex justify-center flex-col space-y-2">
        {searchedArtist.length ? 
            searchedArtist.map((artist, idx) => (
                <div key={idx}  className="flex flex-col text-black text-lg p-3 bg-slate-500 rounded-lg">
                    <div>Name: {artist.name}</div>
                    <div>Albumns: {artist.albums.join(", ")}</div>
                    <div>Average Popularity: {artist.avgPop}</div>
                    <div>Like Counts: {artist.likeCount}</div>
                    <div>Most Pop Genres: {artist.mostPopGenres.join(", ")}</div>
                </div>
            )) : (
            allArtists ? 
            (
                allArtists.map((artist, idx) => {
                    return (
                        <div key={idx} className="flex flex-row text-black text-lg p-3 bg-slate-500 rounded-lg">
                            {artist.artist}
                        </div>
                    );
                })
            ) : (
                <h1 className="text-black">No Artists Found</h1>
            )
            )
        }
      </div>
    </div>
  );
}

async function getArtistStat(name) {
    const albums = await axios.get(
    `http://localhost:8080/artist/${name}/albums`
    );
    const avgPop = await axios.get(
    `http://localhost:8080/artist/${name}/popularity`
    );
    const likeCount = await axios.get(
    `http://localhost:8080/artist/${name}/totalLikes`
    );
    const mostPopGenres = await axios.get(
    `http://localhost:8080/artist/${name}/genres`
    );

    return {
        name: name,
        albums: albums.data,
        avgPop: avgPop.data,
        likeCount: likeCount.data,
        mostPopGenres: mostPopGenres.data
    }
}

async function searchArtists(searchQuery, hook_1, hook_2) {
    console.log("Searching for:", searchQuery);
    // GET /artists/{artist} - get artists by name
    if (!searchQuery) {
      console.log("No search query provided");
      return;
    } else {
      try {
        const artists = await axios.get(
          `http://localhost:8080/artists/${searchQuery}`
        );
        const stats = await Promise.all(artists.data.map(({ artist }) => {
          return getArtistStat(artist)
        }))
        hook_1(stats);
        hook_2(null);
        console.log("Artist found:", artists.data);
      } catch (error) {
        console.error("Error occurred while searching:", error);
      }
    }
  }

async function getAllArtists(hook) {
  try {
    const response = await axios.get(
      `http://localhost:8080/artists`
    );
    console.log("response:", response);
    hook(response.data);
  } catch (error) {
    console.error("Error occurred while getting all artists:", error);
  }
}

const mapStateToProps = (state) => ({
  artist: state.artist,
});

export default connect(mapStateToProps)(Artists);
