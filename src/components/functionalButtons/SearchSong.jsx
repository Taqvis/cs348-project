import { useState } from "react";

export default function SearchSongButton() {
  const [trackName, setTrackName] = useState("");

  const searchSong = () => {
    // your search song logic here
  };

  return (
    <>
      <div>
        <input
          type="text"
          value={trackName}
          onChange={(e) => setTrackName(e.target.value)}
        />
      </div>
      <div className="card">
        <button onClick={searchSong}>Search Song</button>
      </div>
    </>
  );
}
