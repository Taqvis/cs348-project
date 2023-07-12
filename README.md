# cs348-project

To run the frontend, please ensure the backend is setup succesfully and hosted on localhost 8080

###### Setup

1. Install dependencies `npm i`
2. run build `npm run dev`
3. install Moesif CORS extension [link here](https://chrome.google.com/webstore/detail/moesif-origin-cors-change/digfbfaphojjndkpccljibejjbppifbc)
4. ensure MOESIF CORS is active

###### Usage

1. Create an account. Playlists will not work without an account created
2. Login. Logging in will log in for that page reload. reloading will clear your session and require you to login again
3. Data may not refresh instantly. when manipulating data on the search or playlists page, switch to another page and come back for the data to be rendered properly
4. Renaming playlists is not currently implemented and is a future feature. Song names are not broken, they display trackID on purpose
