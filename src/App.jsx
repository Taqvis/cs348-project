<<<<<<< HEAD
import { useState } from "react";
import axios from "axios";
import spotifyLogo from "./assets/favicon.png";
=======
import { useState } from 'react'
import reactLogo from './assets/spotify-icon.png'
import './App.css'
>>>>>>> 9782225 (merge)

export default function App() {

  const [searchQuery, setSearchQuery] = useState("");

  const [displayedSongs, setDisplayedSongs] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Searching for:", searchQuery);
    
    searchSongs(searchQuery, setDisplayedSongs)

    setSearchQuery("");
  };

  return (
    <div className="space-y-8">
      <style>
        {`
          @keyframes logo-spin {
            from {
              transform: rotate(0deg);
            }
            to {
              transform: rotate(360deg);
            }
          }

          @media (prefers-reduced-motion: no-preference) {
            .logo {
              animation: logo-spin infinite 20s linear;
            }
          }
        `}
      </style>

      <div className="p-6 flex flex-col items-center">
        <a href="https://open.spotify.com/" target="_blank">
          <img
            src={spotifyLogo}
            className="logo h-24 md:h-26 lg:h-26 xl:h-26"
            alt="Spotify Logo"
          />
        </a>
        <h1 className="text-center font-semibold text-4xl pt-6">
          CS348 Project
        </h1>
      </div>

      <div className="flex justify-center h-full w-full">
        <form onSubmit={handleSubmit} className="flex space-x-2">
          <input
            type="text"
            placeholder="Search for a song"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="bg-neutral-600 rounded-2xl px-8 py-4 w-96"
          />
          <button
            type="submit"
            className="bg-green-600 px-4 py-2 rounded-2xl w-32"
          >
            Search
          </button>
        </form>
      </div>

      <div className="flex justify-center h-full w-full bg-neutral-800">
        <p>
          {(displayedSongs && displayedSongs.length > 0)? displayedSongs.map((displayedSongs) => (displayedSongs.track_name)) : <p>No songs found</p>}
        </p>
      </div>
    </div>
  );
}

async function searchSongs(searchQuery, hook) {
  try {
    const response = await axios.get(`http://localhost:8080/test/tracks/${searchQuery}`);
    const songs = response.data;
    hook(songs);
    console.log("Songs found:", songs);
  } catch (error) {
    console.error("Error occurred while searching:", error);
  }
}

