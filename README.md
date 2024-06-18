# This is the PD2 final project-mahjong
# Contributers ：魏靖豪、譚永祺、涂睿倫、劉奕辰
[flowchart.pdf](https://github.com/user-attachments/files/15876938/Untitgled.pdf)
![image](https://github.com/edwei06/pd2-final-project/assets/80562201/b26b03e2-dbc3-4de2-b61e-8b4ea8db63a6)

## 1. 素材發想:
* 參考市面上大多數麻將程式發想連接方式、畫面元素
## 2. 系統設計:
* 透過.java檔之間的物件交流來實現玩家間的手牌、吃牌堆的新
* 創建一個server讓4個客戶端可以共同遊玩
* 使用socket做client與server間的資訊交流
## 3. 分工與實作:
* 涂睿倫:製作GameServer與Client的串接
* 譚永祺:製作後端邏輯
* 魏靖豪:製作Client gui
* 劉奕辰:製作Server gui
## 4. 網路連接與管理:
* 在Client Class 主要用於從Server接收遊戲數據並更新GUI
* ClientHandler Class 用於在Server端處理每個客戶端的交流，每個Client都會有對應的ClientHandler 實例
* Server Class 主要負責伺服器的運行，接受Client的連接並處理數據包
## 5. 用戶介面:
* 取樣於市面上主流的麻將程式
* 在介面的連接上遇到許多挑戰，學到許多技術
* 未來希望可以改善將介面與遊戲邏輯整合
## 6. 部屬:
* 在同一個網域下啟動Server.java
* 並且在啟動Client.java並且在終端進行遊戲操作
## 7. 未來更新:
* 讓遊戲可以進行一圈甚至是一將
* 優化吃碰槓胡牌邏輯
* 串接gui與後端
