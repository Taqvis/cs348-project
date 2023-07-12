import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { connect } from "react-redux";
import { setUsername } from "../redux/actions";

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
      .get("http://localhost:8080/test/login/" + username + "/" + password)
      .then((response) => {
        console.log(response);
        if (response.data === 'success' && response.status === 200) {
          props.setUsername(username);
          navigate("/playlists");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div class="bg-slate-600 h-screen px-10 py-10 w-full">
      <div class="w-full max-w-xl mx-auto">
        <form
          class="LgnForm max-w-sm mx-auto shadow-2xl bg-white rounded-lg pt-6 pb-8 mb-4 px-8"
          onSubmit={submitHandler}
        >
          <div class="MskForm">
            <div class="mb-4">
              <label class="block text-gray-700 text-sm font-bold mb-2">
                Username
              </label>
              <input
                class="login-register-textBox focus:outline-none focus:shadow-outline apperance-none"
                type="text"
                placeholder="Username"
                value={username}
                onChange={(event) => setUsername(event.target.value)}
              />
            </div>
            <div class="mb-6">
              <label class="block text-gray-700 text-sm font-bold mb-2">
                Password
              </label>
              <input
                class="login-register-textBox focus:outline-none focus:shadow-outline apperance-none"
                type="password"
                placeholder="******************"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
              />
            </div>

            {errorMessage && <h5 class="text-red-600">{errorMessage}</h5>}

            <div class="flex items-center justify-between">
              <input
                type="submit"
                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                value={"Sign In"}
              ></input>
            </div>
            <p class="mt-2 text-black-500 text-xs">
              Don't have an account? <br />
              <a href="/register" class="text-blue-700 font-bold py-4">
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
});

const mapDispatchToProps = {
  setUsername,
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);