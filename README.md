# This is the PD2 final project-mahjong
# Contributers ：魏豪豪、涂倫倫、劉辰辰
[flowchart.pdf](https://github.com/user-attachments/files/15876938/Untitled.pdf)

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
