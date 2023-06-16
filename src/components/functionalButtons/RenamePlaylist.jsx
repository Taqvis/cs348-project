import { useState } from "react";

export default function RenamePlaylistButton() {
  const [newName, setNewName] = useState("");

  const renamePlaylist = () => {
    // your rename playlist logic here
  };

  return (
    <>
      <div>
        <input
          type="text"
          value={newName}
          onChange={(e) => setNewName(e.target.value)}
        />
      </div>
      <div className="card">
        <button onClick={renamePlaylist}>Rename Playlist</button>
      </div>
    </>
  );
}
