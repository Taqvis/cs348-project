import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Reccomendations = () => {
  const navigate = useNavigate();

  const [name, setName] = React.useState("");
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [errorMessage, setErrorMessage] = React.useState("");

  const submitHandler = (event) => {
    event.preventDefault();

    setName(name.trim());
    setUsername(username.trim());
    setPassword(password.trim());

    if (username === "" || password === "" || name === "") {
      setErrorMessage("Please fill all the fields");
      return;
    }

    axios
      .post("http://localhost:8080/register", {
        username: name,
        displayName: username,
        password: password,
      })
      .then((response) => {
        console.log(response);
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div className="bg-slate-600 h-screen px-10 py-10 w-full">
      <div className="w-full max-w-xl mx-auto">
        <form
          className="LgnForm max-w-sm mx-auto shadow-2xl bg-white rounded-lg pt-6 pb-8 mb-4 px-8"
          onSubmit={submitHandler}
        >
          <div className="MskForm">
            <div className="mb-4">
              <label className="block text-gray-700 text-sm font-bold mb-2">
                Name
              </label>
              <input
                className="login-register-textBox focus:outline-none focus:shadow-outline apperance-none"
                type="text"
                placeholder="Name"
                value={name}
                onChange={(event) => setName(event.target.value)}
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 text-sm font-bold mb-2">
                Username
              </label>
              <input
                className="login-register-textBox focus:outline-none focus:shadow-outline apperance-none"
                type="text"
                placeholder="Username"
                value={username}
                onChange={(event) => setUsername(event.target.value)}
              />
            </div>
            <div className="mb-6">
              <label className="block text-gray-700 text-sm font-bold mb-2">
                Password
              </label>
              <input
                className="login-register-textBox focus:outline-none focus:shadow-outline apperance-none"
                type="password"
                placeholder="******************"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
            </div>

            {errorMessage && <h5 className="text-red-600">{errorMessage}</h5>}

            <div className="flex items-center justify-between">
              <input
                type="submit"
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                value={"Create Account"}
              ></input>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Reccomendations;
