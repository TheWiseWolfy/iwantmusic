import React from 'react';

const PlaylistsList = ({ playlists }) => (
  <ul>
        {
        playlists && playlists.length > 0 && playlists.map((playObj, index) => (
            <li
             key={playObj.id}>{playObj.playlist_name}

                <ul>
               {  playObj.songs.length > 0 ?  <p>Songs:</p> :""}
                {
                    playObj.songs.map((song, index) => (
                        <li key={song.songId}>{song.songName} <p>{song.songLink}</p></li>
                    ))
                    }
                </ul>
            </li>
            ))
        }
  </ul>
);

export default PlaylistsList;