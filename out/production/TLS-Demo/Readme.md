# Wordle

GENERAL

  Wordle is a command line implementation of the well known game created by Josh Wardle.
  The game works by starting the server then the client, you're then prompted to guess
  a 5-letter word that the server has selected. The server will in turn return you a hint
  consisting of lowercase letters for letters in the target word but not in the correct position, 
  and a capital letter for a correct letter in it's correct position. The is won when the user
  has correctly guessed the servers selected word. The server found in the Wordle game makes use
  of a Thread pool, this allows for concurrency on the server whilst managing resource limitations.
  This means that you can feel free to run multiple clients at the same time, just note that if you
  hit the servers limit on client connections, you will need to wait until one of the clients finishes
  before your game can begin.

INSTALLATION

  Wordle does not require installation, the game is simply run through running the startServer.sh
  then the startClient.sh.

OPERATING INSTRUCTIONS

  [SERVER]

    [RUN]  
    To start the wordle server, run the startServer.sh script, passing it a command line flag defining
    the port number you wish the server to bind to.

    [TERMINATE]
    The wordle server will only terminate by using ctrl + c. As the server is implemented with a thread 
    pool, server endpoints may end due to:
    - A bad message is recieved
    - The client loses connection
    - The client wins the game
    However the Server itself will create another endpoint on a new client connection. So please ensure that
    you exit the server properly using ctrl + c.    
  
  [CLIENT]

    [RUN]
    To start the wordle client, run the startClient.sh script, passing it 2 command line flags defining
    the hostname and port number of the server. 

    [TERMINATE]
    The wordle client will terminate based on the given terms:
    - If the client has won the game
    - If the client recieves an unknown message by the server
    - If the connection to the server is lost
    - By using ctrl + c

  [GENERAL]

  To run the game, start the server using the instructions stated in [SERVER]. then once the server has
  started, you can begin the game by running the client using the instructions stated in [CLIENT]. 
  

CONFIGURATION
  
  Wordle only requires that you configure a port for the server and a hostname and port for the clients,
  these will be configured as command-line added to the calls to the startClient.sh and startServer.sh
  scripts.

NOTES 

  - The server is hard-coded to support 20 clients at a time.
  - If you are unable to run the provided shell scripts, enable the execute permission for the scripts and run them again.

CREDITS

  This game was written by Ashley Travaini to be used for Assignment 2 of COSC340 at UNE. 

ACKNOWLEDGEMENTS

  I acknowledge and thank Josh Wardle, the original creator of the Wordle game, as this is only
  a command-line remake of the original.



  
   
