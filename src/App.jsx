import { BrowserRouter, NavLink, Routes, Route } from "react-router-dom";
import React from "react";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Search from "./pages/Search";
import Playlists from "./pages/Playlists";
// import Test from "./pages/test";

const globalState = {
  username: null,
};

const globalStateContext = React.createContext(globalState);

export default function App() {
  return (
    <div>
      <BrowserRouter>
        <nav class="border-gray-200 dark:bg-gray-900">
          <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
            <div class="flex items-center">
              <span class="self-center text-2xl font-semibold whitespace-nowrap text-white">
                CS348 Project
              </span>
            </div>

            <div className="hidden w-full md:block md:w-auto">
              <ul class="navbarContainer">
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
              {/* <Route path="/test" element={<Test />} /> */}
            </Routes>
          </div>
      </BrowserRouter>
    </div>
  );
}
