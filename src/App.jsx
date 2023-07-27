import { BrowserRouter, NavLink, Routes, Route } from "react-router-dom";
import React from "react";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Search from "./pages/Search";
import Playlists from "./pages/Playlists";
import Artists from "./pages/Artists"
import Leaderboard from "./pages/Leaderboard"
import Recommendation from "./pages/Recommendation";
// import Test from "./pages/test";

export default function App() {
  return (
    <div className="bg-slate-600 h-screen">
      <BrowserRouter>
        <nav className="border-gray-200 dark:bg-gray-900">
          <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
            <div className="flex items-center">
              <span className="self-center text-2xl font-semibold whitespace-nowrap text-white">
                CS348 Project
              </span>
            </div>

            <div className="hidden w-full md:block md:w-auto">
              <ul className="navbarContainer">
                <li>
                  <NavLink className="navbarElement" to="/register">
                    Register
                  </NavLink>
                </li>
                <li>
                  <NavLink className="navbarElement" to="/login">
                    Login
                  </NavLink>
                </li>
                <li>
                  <NavLink className="navbarElement" to="/search">
                    Search
                  </NavLink>
                </li>
                <li>
                  <NavLink className="navbarElement" to="/playlists">
                    Playlists
                  </NavLink>
                </li>
                <li>
                  <NavLink className="navbarElement" to="/artists">
                    Artists
                  </NavLink>
                </li>
                <li>
                  <NavLink className="navbarElement" to="/leaderboard">
                    Leaderboard
                  </NavLink>
                </li>
                <li>
                  <NavLink className="navbarElement" to="/recommendation">
                    Recommendations
                  </NavLink>
                </li>
              </ul>
            </div>
          </div>
        </nav>

          <div>
            <Routes>
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route path="/search" element={<Search />} />
              <Route path="/playlists" element={<Playlists />} />
              <Route path="/artists" element={<Artists />} />
              <Route path="/leaderboard" element={<Leaderboard />} />
              <Route path="/recommendation" element={<Recommendation />} />
              {/* <Route path="/test" element={<Test />} /> */}
            </Routes>
          </div>
      </BrowserRouter>
    </div>
  );
}
