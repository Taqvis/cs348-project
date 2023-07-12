import React, { useState } from 'react';

const PlaylistPopup = ({ onClose, onConfirm }) => {
  const [playlistName, setPlaylistName] = useState('');

  const handleConfirm = () => {
    onConfirm(playlistName);
    onClose();
  };

  return (
    <div className="fixed w-full h-full flex items-center justify-center">
      <div className="bg-slate-800 p-10 -translate-y-10 rounded-lg">
        <h3 className="text-lg pb-3">Enter Playlist Name</h3>
        <input
          type="text"
          className="w-full p-2 rounded-lg text-black"
          value={playlistName}
          placeholder="Playlist Name"
          onChange={(e) => setPlaylistName(e.target.value)}
        />
        <div className="flex mt-5">
          <button onClick={handleConfirm}>Confirm</button>
          <button className='ml-auto' onClick={onClose}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default PlaylistPopup;
