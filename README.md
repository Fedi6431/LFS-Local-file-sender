# LFS-Local File Sender

## Index

*   [Description](#description)
*   [Installation](#installation)
*   [Usage](#usage)
*   [Errors](#errors)
*   [Warnings](#warnings)
*   [Report a bug](#bug-report)
*   [Credits](#credits)

## Description

The program Local File Sender or more simple LFS is a java program made with Swing.  
The propuse of it is to send file of all dimension to a pc from another with IPv4.  
It's a very user friendly program because all the option are visible and if something or some input is wrong, the program throw an GUI error with JOptionPane.  
Why i made it?  
Becuase i wanted to send a file from my local pc to another local pc, but i saw that none made a program like that (maybe because is useless **:/** ).

![Screenshoot of the main page of the program (Sender Panel)](DOCUMENTATION/images/SenderPanel.png)

## Installation

### Windows

1.  Ensure you have [open JDK](https://openjdk.org/projects/jdk/17/) or [Java installed](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (version 17 or higher)
2.  Download the code for the [official github page](https://github.com/Fedi6431/LFS-Local-File-sender/releases) `https://github.com/Fedi6431/LFS-Local-File-sender/releases`  
    You can use git with the command `git clone https://github.com/Fedi6431/LFS-Local-File-sender.git` or `git pull https://github.com/Fedi6431/LFS-Local-File-sender.git`  
    But you can download it directly for the github page.  
    

## Usage

To use the **jar** program, go in the `out\artifacts\` and execute the command

`java -jar LFS.jar`  

To use the **java** program, go in the `src\` and execute the command

`java LFS.java`

## Errors

Possible error messages that the user may see in the program:

*   Exception

Depends of what Exception is triggered in the program (Message in the panel when the exception happend).

*   Invalid ip

Triggered when the ip that the user want to set is invalid.

*   Invalid file path

Triggered when the user want send a file but the path isn't specified/selected.

*   Invalid Input

Triggered when the user write an invalid input for the port instead of a port number (integer).

Possible errors that the user may see in the log:

*   FileNotFoundException

Occurs when the specified file does not exist.

*   NullPointerException

Occurs when a program attempts to use an object reference that has not been initialized.

*   UnknownHostException

Occurs when the IP address of a host could not be determined.

*   IOException

Occurs when an input or output operation fails or is interrupted.

## Warnings

Possible warn that can be showed in the program

*   Sender service

The "sender service" isn't an error, is only the sender status service. But it can warn you if another sender thread is active and you want active another one.

*   Server service

The "server service" isn't an error, is only the server status service (like the sender service). But it can warn you if another server thread is active.

*   SocketTimeoutException

Triggered if the connection max timeout is reached or if the server is unavaible (so the client can't reach the server and the timeout start)

## Report a bug

Did you find a bug?  
[Make a pull!](https://github.com/Fedi6431/LFS-Local-File-sender/pulls)  
[Make a new issue!](https://github.com/Fedi6431/LFS-Local-File-sender/issues)

## Credits

This will be a very long list XD

### Java developer: [Fedi6431](https://github.com/Fedi6431)

### Html developer: [Fedi6431](https://github.com/Fedi6431)

### Creator: [Fedi6431](https://github.com/Fedi6431)

© Fedi6431
