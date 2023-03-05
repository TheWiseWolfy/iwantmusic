package com.unwise.playlist_service.services;

import com.unwise.playlist_service.models.Playlist;
import com.unwise.playlist_service.models.PlaylistSong;
import com.unwise.playlist_service.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

	@Autowired
	PlaylistRepository playlistRepository;

	public Optional<Playlist> getPlaylistById(String id){
		return playlistRepository.findById(id);
	}



	public List<Playlist> getAllPlaylists(){
		return playlistRepository.findAll();
	}

	public Playlist createNewPlaylist(Playlist playlist){

		playlist.setCreationDate(LocalDate.now() );
		try {
			var playlistObject = playlistRepository.save(playlist);
			return playlistObject;
		}catch (Exception e){
			throw e;
		}
	}

	public Playlist savePlaylist(Playlist playlist){
		return playlistRepository.save(playlist);
	}

	public void deletePlaylist(String id){
		playlistRepository.deleteById(id);
	}




	public Playlist addSongsToPlaylist(String id, List<PlaylistSong> newSongs) throws Exception {
		Optional<Playlist> playlistOptional = playlistRepository.findById(id);

		if(playlistOptional.isPresent()) {
			Playlist playlist = playlistOptional.get();
			var currentSongs = 	playlist.getSongs();
			currentSongs.addAll(newSongs);
			playlist.setSongs(currentSongs );
			playlistRepository.save(playlist);

			return playlistOptional.get();
		}

		System.out.println("Playlist not found.");
		throw new Exception("Playlist not found.");

	}
}
