import React, { useEffect, useState } from "react";
import axios from "axios";
import { connect } from "react-redux";

function Leaderboard({ username, password }) {
  const [leaderboardSpots, setLeaderboardSpots] = useState(null);

  const auth = 'Basic ' + btoa(username + ':' + password);

  useEffect(() => {
    getLeaderboard(auth, setLeaderboardSpots);
  }, []);

  return (
    <div className="bg-slate-600 h-screen px-10 py-10 w-full">
        <div className="flex justify-center flex-col space-y-2">
        {leaderboardSpots ? <div
            className="flex flex-row justify-between font-bold text-black text-lg p-3 bg-slate-500 rounded-lg"
        >
            <div>Position</div>
            <div>Likes</div>
            <div>Username</div>
        </div> : <div></div>}
        {leaderboardSpots ? (
          leaderboardSpots.map((value, idx) => (
            <div
              key={idx}
              className="flex flex-row justify-between text-black text-lg p-3 bg-slate-500 rounded-lg"
            >
              <div>#{value.position}</div>
              <div>{value.likes}</div>
              <div>{value.ownerUsername}</div>
            </div>
          ))
        ) : (
          <h1 className="text-black">No Liked Playlists</h1>
        )}
      </div>
    </div>
  );
}

async function getLeaderboard(auth, hook) {
  try {
    const response = await axios.get(`http://localhost:8080/leaderboard`, {
      headers: {
        Authorization: auth,
      },
    });
    console.log("response:", response);
    hook(response.data);
  } catch (error) {
    console.error("Error occurred while getting leaderboard:", error);
  }
}


const mapStateToProps = (state) => ({
  username: state.username,
  password: state.password,
});

export default connect(mapStateToProps)(Leaderboard);