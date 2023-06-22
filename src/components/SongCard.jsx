import React from "react";

export default function SongCard(props) {
  const { song } = props;
  return (
    <div className=".border-solid .border-4 .border-light-blue-500">
      <div class="inline-block">{song.title}</div>
      <p class="inline-block">{song.artist}</p>
      <h1 class="underline">Hello world!</h1>
    </div>
  );
}
