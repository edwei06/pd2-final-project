# This is the PD2 final project-mahjong
# Contributers ：魏豪豪、涂倫倫、劉辰辰
![Flowchart]([https://example.com/flowchart.png](https://cdn.discordapp.com/attachments/1249350463451566261/1252358039605088308/Untitled.pdf?ex=6671ecd0&is=66709b50&hm=605c6716d90738b547467f3916d1ad4f31263bf1945686bdbc5768008dd4ab1d&) "Flowchart")

## 1. Requirements Gathering
* Connectivity: Determine how players will connect to the host player's server.
* Hosting Requirements: Specify the requirements for a player to become a host.
## 2. System Design
* Hybrid Client-Server Model: The host player's application acts as both a client and server.
* Dynamic IP Handling: Design for dynamic IP environments, including NAT traversal techniques if necessary.
## 3. Game Components Design
* Host Selection: Logic to determine and switch the host if the current host leaves or disconnects.
* Game Session Management: Manage the creation and lifecycle of a temporary game session.
## 4. Network Programming
* Host Setup: Code to initialize a host session and accept connections.
* Client Connection: Mechanisms for clients to discover and connect to the host.
* Connection Stability: Implement methods to handle disconnections and reconnections.
## 5. User Interface
* Host Controls: Interface elements for the host to manage game settings and player invitations.
* Connection Status: Feedback for players about connection health and host status.
## 6. Testing
* Host Migration Testing: Ensure the game can handle the migration of host duties smoothly.
* Network Resilience Testing: Test how the game copes with network issues like latency and packet loss.
## 7. Deployment
* Direct Play Invitations: Allow players to invite others directly via an in-game interface or external methods like email or social media.
* Executable Distribution: Ensure the game client can be easily downloaded and run by players.
## 8. Maintenance and Updates
* Host Reliability: Regularly update host-related functionalities to improve stability and performance.
* Dynamic Content Updates: Implement a system for updating game content without needing complete reinstallation.
* Additional Considerations
* Firewall and Router Configuration: Guide users on configuring firewalls and routers, which may block incoming connections to a host.
* Security: Implement encryption for data transmission and consider security implications of a player-hosted model.
