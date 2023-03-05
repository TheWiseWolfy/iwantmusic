1. **ContentService**

![image](https://user-images.githubusercontent.com/21224518/213922545-45f806b7-aaab-4bf4-8915-c3c0d7091c86.png)

Este un serviciu REST care implementeaza urmatoarea diagrama relationala: 
![image](https://user-images.githubusercontent.com/21224518/213922700-0e2fd3e9-6b9f-4427-bed9-8c5477c95305.png)

Lagaturile de **Many to Many** si **Many to one** sunt create folosind adnotari din javax.persistence gasite. Tabela Credits reprezinta legatura **Many to Many** dintre Artists si Content si pastreaza indexul fiecarui artist in legatura cu o melodie (0 -artist principal, 1,2.. -artisti secundari) 

Pentru a evita problema de recursivitate infinita care apare cand incercam sa afisam tabelul de legatura Credits, in ContentController folosim un DTO care previne aplicatia din a intra recursiv inapoi in Content si mai apoi in Credits, si mai apoi in Content si asa mai departe la infinit.
Se pot face cereri de create pe mai multe nivele, care se vor propaga in tablea content, song_tags si tag_types.

![image](https://user-images.githubusercontent.com/21224518/213923703-a37fd500-904f-431c-8ede-62a5ef6eb4ad.png)

Tabelele din mariaDB sunt partitionate pe mai multi utilizatori iar fiecare are acces doar la baza de date relevanta. Microserviciul mai are integrata documentatie autogenerata in stilul springdoc la locatia /api/docs.

<img src="https://user-images.githubusercontent.com/21224518/213923799-2c8ab116-faae-49ab-b72c-a2794da8c96b.png"  height="400">

Atat serverul MongoDB cat si serverul MariaDB ruleaza in containere de docker locale.
![image](https://user-images.githubusercontent.com/21224518/213926280-60e99fec-4dd4-424c-9a5e-60ed6fbf4490.png)

2. **PlaylistService**

Serviciu rest bazat pe o colectie in MongoDB. 

![image](https://user-images.githubusercontent.com/21224518/213924310-a21741dc-e5ce-47c2-b80a-36ef82b26358.png)
![image](https://user-images.githubusercontent.com/21224518/213926357-7c627ef0-0c3e-4025-9826-51cdba7505c1.png)

Structura unui raspuns de Get arata asa: 

![image](https://user-images.githubusercontent.com/21224518/213924487-485d12c4-41e8-4456-845c-61908d74ce7b.png)

3. **AuthenticatorService**
Un serviciu SOAP scris in Python. Acesta pune la dispozitie 2 metode:

    -authenticate( username, password) -> token
    -validate_token(self, token) -> { isValid, id, role}

Acesta pastrala o lista de utilizatori intr-o baza de date MariaDB. Secretul este citit dintr-o fila locala. Token-ul incorporeaza id-ul, data de expirare, rolul utilizatorului si un identificator unic. Fuctia de autentificare va returna un token atunci cand datele de autentificare sunt corecte. Fuctia de validare va returna date utile despre token.

4. **GatewayService**

![image](https://user-images.githubusercontent.com/21224518/213924931-6fb4d54a-3fdb-47c5-9f36-b44ae52b77ea.png)

Prin serviciul de Gateway avem posibilitatea de a ne autentifica cu username-ul si parola. Folosind SOAPService, Gateway-ul va confirma datele de intrare si va recupera un token. Mai apoi token-ul este folosit la validarea cerelilor. Comunicarea cu celelante servici este realizata in ContentService, PlaylistService si SOAPService.

```java
  @PutMapping("/playlist/{playlist_id}")
    public ResponseEntity<?> putPlaylist(@RequestBody Playlist playlist, @PathVariable String playlist_id,
                                         @RequestHeader(value = "Authorization", required = false) String token ){
        var tokenValidation =  soapService.isValidToken(token);

        //Verificam daca exista un token si e valid
        if(tokenValidation.isPresent() && tokenValidation.get().isValid() ){
            String id = tokenValidation.get().getId();
            Integer role = Integer.valueOf( tokenValidation.get().getRole() );

            //Verificam daca exista deja un playlist cu id-ul asta
            Optional<Playlist> recoveredPlaylist =  playlistService.getPlaylistById(playlist_id);

            //Daca nu exista deja un playlist cu ID-ul asta, sau playlist-ul e detinut de persoana care face request-ul
            //atunci putem continua
            if(recoveredPlaylist.isEmpty() || Objects.equals(id, recoveredPlaylist.get().getOwner_id() )){
                //Do we need to complete any song ?
                for (int i = 0; i < playlist.getSongs().size(); i++) {
                    var song = playlist.getSongs().get(i);
                    if(song.getSongName().isEmpty() || song.getSongLink().isEmpty() ){

                        var songDetails = contentService.getContentByID(song.getSongId() );
                        if(songDetails.isPresent()){
                            playlist.getSongs().get(i).setSongName(songDetails.get().getContent_name());
                            playlist.getSongs().get(i).setSongLink(songDetails.get().getLinks().getSelf().getHref());
                        }
                    }
                }

                HttpResponse<String> response =  playlistService.putPlaylist(playlist,playlist_id );
                if(response.statusCode() == 200) {
                    return new ResponseEntity(HttpStatus.OK);
                }else if (response.statusCode() == 204){
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
```
Acest request demonstreaza calitatea de orchestrator al Gateway-ului. Mai intai de toate, valideaza token-ul trimis inpreuna cu request-ul. Utilizatorul poate modifica un playlist doar daca este detinatorul oginal al acestuia sau daca este administrator. Mai apoi, aplicatia client poate trimite doar ID-urile melodiilor care sunt dorite in playlist, iar Gateway-ul va recupera celelante detalii din ContentService si va realiza un request cu toata informatia necesara catre PlaylistService.

4. **ReactAplication**

In pagina de login, avem posibilitatea sa ne introducem datele. Aplicatia va realiza o cerere catre gateway, iar daca datele sunt corecte, aplicatia va recupera un token si va permite accesul la dashboard. Logica de logare este integrata in componenta AuthProvider. Aceasta ne pune la dispozitie o fuctie de loginCallback, propietatea token is propietatea isAuthenticated. In pagina Dashboard, pot folosi propietatea isAuthenticated pentru a afisa sau nu token-ul.

```js
useEffect( authRequest, [pendingAutentification] ) //AuthRequest is executed only when pendingAutentification is modified

    function loginCallback( username, password){
        setUsername(username)
        setPassword(password)
        setPendingAutentification(true)
    }

    function authRequest() {
        if(!isAuthenticated && pendingAutentification){

            const body = {
                "username": username,
                "password": password
            }

            fetch('http://localhost:8095/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            })
                .then(response => {
                    if (response.status === 200) {
                        response.json().then(res => {
                            setToken(res.jwt)
                            setIsAuthenticated(true)
                        })
                    } else {
                        console.log("Autentification failed.") 
                    }
                    setPendingAutentification(false)
                })
        }
    }

    const value =  {
        isAuthenticated: isAuthenticated,
        token: token,
        //Punem la dispozitie o fuctie de login copiilor prin context
        login: useCallback(loginCallback, []) 
    }

    return (
        <AuthContext.Provider value={value}>
        {props.children}
        </AuthContext.Provider>
    );
```

Aplicatia mai listeaza playlist-urile recuperate prin Gateway de la PlaylistService.

![image](https://user-images.githubusercontent.com/21224518/213925765-f996ea1d-a79f-4ec8-8ffd-9922f6159642.png)

Configuratiile din postman au fost exportate in folderul Logs.


