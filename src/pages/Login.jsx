import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { connect } from "react-redux";
import { setUsername, setPassword } from "../redux/actions";

const Login = (props) => {
  const navigate = useNavigate();

  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [errorMessage, setErrorMessage] = React.useState("");

  const submitHandler = (event) => {
    event.preventDefault();

    setUsername(username.trim());
    setPassword(password.trim());

    if (password === "" || username === "") {
      setErrorMessage("Please fill all the fields");
      return;
    }
    axios
      .get("http://localhost:8080/tracks", {
        headers: {
          Authorization: "Basic " + btoa(username + ":" + password),
        },
      })
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          props.setUsername(username);
          props.setPassword(password);

          navigate("/playlists");
        }
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
                value={"Sign In"}
              ></input>
            </div>
            <p className="mt-2 text-black-500 text-xs">
              Don't have an account? <br />
              <a href="/register" className="text-blue-700 font-bold py-4">
                Create an account
              </a>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

const mapStateToProps = (state) => ({
  username: state.username,
  password: state.password,
});

const mapDispatchToProps = {
  setUsername,
  setPassword,
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);