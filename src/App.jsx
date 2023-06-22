import { useState } from "react";
import reactLogo from "./assets/spotify-icon.png";
import "./App.css";

// Import your components
import RenamePlaylistButton from "./components/functionalButtons/RenamePlaylist";
import SearchSongButton from "./components/functionalButtons/SearchSong";
import DeleteSongButton from "./components/functionalButtons/DeleteSong";
import CreatePlaylistButton from "./components/functionalButtons/CreatePlaylist";
import AddSongToPlaylistButton from "./components/functionalButtons/AddSong";

import SongCard from "./components/SongCard";

function App() { 
  return (
    <>
      <div>
        <a href="https://open.spotify.com/" target="_blank" rel="noreferrer">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>CS348 Project</h1>

      <SongCard song={{ title: "Example Title", artist: "Example Name" }} />

      {/* display components side by side */}
      <div class="space-x-4">
        <div class="inline-block">
          <RenamePlaylistButton />
        </div>
        <div class="inline-block">
          <SearchSongButton />
        </div>
      </div>
      <div class="inline-block">
        <DeleteSongButton />
        <CreatePlaylistButton />
        <AddSongToPlaylistButton />
      </div>
    </>
  );
}

export default App;
