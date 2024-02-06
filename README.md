# 자율주행 자동차 웹캠 실시간 스트리밍 using WebRTC :car:

<div align=center>
  <img src="https://github.com/jaeyeoneee/WebRTC/assets/109527136/027af870-6779-426b-8483-9948df6cb967" width="500">
</div>

> DGIST 미래자동차연구부 근로장학생
>
> 개발기간: 2023.11 ~ 2024.2
>
> [개발 블로그](https://velog.io/@jaeyeoneee/WebRTC%EB%A1%9C-%EC%9B%B9%EC%BA%A0-%EC%98%81%EC%83%81-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EC%A0%84%EC%86%A1%ED%95%98%EA%B8%B0-WebRTC%EB%9E%80)


## 프로젝트 소개
* WebRTC를 이용해 자율주행 자동차와 사용자 간에 P2P 연결을 맺고, 자율주행 자동차 웹캠 영상을 실시간으로 스트리밍 하는 프로그램
* 채팅 기능을 통해 사용자가 자동차 프로그램에 명령어를 전달할 수 있다.
* 두 단계로 나누어 1단계는 자바스크립트 webrtc API로 기능을 구현하고, 검증이 완료되면 이후에 웹캠 측을 파이썬 aiortc로 변경한다.

#### STEP 1: 해당 레포지토리
 * 스프링 부트 stomp 브로커를 이용해 signaling 서버를 구현한다.
 * 웹캠과 사용자는 자바스크립트 WebRTC API를 이용한다.
 * 사용자가 웹캠 중 하나를 선택하면, 서버를 통해 정보가 교환되고 P2P 연결을 맺어 영상이 실시간 전송된다.
 * 사용자 인원 제한, 데이터 채널를 통한 사용자-> 웹캠 메시지 전송 기능 등을 추가한다.
 * 구현이 끝나면 테스트해본다.

#### STEP 2:  [aiortc 레포지토리](https://github.com/jaeyeoneee/WebRTC-aiortc)
 * 웹캠 측 자바스크립트 코드를 파이썬 aiortc 코드로 바꾼다.
 * aiortc를 사용함으로 openCV로 웹캠 영상을 더욱 정교하게 처리할 수 있다.

##  Getting Started
### Requirements
For building and running the application you need:
* [Oracle JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)

### Installation
```bash
git clone https://github.com/jaeyeoneee/WebRTC.git
cd WebRTC
```

### Execution
1. Build the application
```bash
./gradlew build
```

2. Run the application
```bash
java -jar build/libs/signaling-0.0.1.jar
```
3. Register a webcam

   
    Visit "/webcam" to register a webcam.

4. Select a webcam video and streaming URL

    
    Visit "/client" to select a webcam video and get the streaming URL.

## Stacks :book:
**Environment**
<p>
  <span><img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"></span>
</p>


**Development**
<p>
  <span><img src="https://img.shields.io/badge/JAVA-437291?style=for-the-badge&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/WebRTC-333333?style=for-the-badge&logo=WebRTC&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"></span>
</p>

**Communication**
<p>
  <span><img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/Flow%20Team-9600F8?style=for-the-badge&link=https://flow.team/kr/index"></span>
</p>

## 화면 구성
* 웹캠 접속(/webcam)]
<div>
  <img src="https://github.com/jaeyeoneee/WebRTC/assets/109527136/4bab001f-b9cd-4e58-84ce-15701c4afdc9" width="400">
</div>

 > * 사용자에게 스트리밍하기를 원하는 웹캠은 "/webcam"에 접속한다. 
 > * 시그널링 서버에 웹캠 정보(고유 id, display name)를 전송하고 사용자를 기다린다. 
 > * 현재 자신의 웹캠 영상을 볼 수 있다.

* 사용자 웹캠 선택(/client)
<div>
  <img src="https://github.com/jaeyeoneee/WebRTC/assets/109527136/f4a66f80-7d48-4354-89e8-ed01f8265543" width="400">
</div>

 > * "/client"에 접속하면 시그널링 서버에서 웹캠 정보를 받아와 버튼으로 보여준다.
 > * 웹소켓과 이벤트 리스너를 통해 웹캠이 변경될 경우 동적으로 버튼이 변경된다.

* 사용자 웹캠 영상 스트리밍
<div>
  <img src="https://github.com/jaeyeoneee/WebRTC/assets/109527136/47c6d4f0-3783-4077-9144-efd088a5da07" width="400">
</div>

 > * 사용자가 웹캠 중 하나를 선택하면 시그널링 서버 stomp 브로커를 통해 sdp, ice candidate를 교환하고 p2p 연결을 맺는다. 
 > * 웹캠 영상을 수신해 볼 수 있다.
 > * webrtc 데이터 채널을 이용해 웹캠에 메시지 전송이 가능하다.
 > * (현재는 웹캠 측에서 메시지를 받으면 "전송 성공"이라는 메시지를 전달해준다. 목적에 맞게 변경 가능하다.)

* 인원 수 초과 페이지
<div>
  <img src="https://github.com/jaeyeoneee/WebRTC/assets/109527136/7734efb2-079f-4000-952a-900f552fef5f" width="400">
</div>

 > * 영상 품질을 위해, 정해둔 특정 인원을 초과하면 접속이 제한된다. 

* 연결 상태 변경 안내 페이지
<div>
  <img src="https://github.com/jaeyeoneee/WebRTC/assets/109527136/7428902c-5443-4af9-8dfb-1162e3675836" width="400">
</div>

 > * 웹캠과의 P2P 연결에 문제가 발생하는 경우(웹캠 프로그램이 종료되는 경우 등) 해당 페이지로 리다이렉트된다.
## 아키텍처
```
├─.gradle
├─.idea
├─build
├─gradle
├─signaling-server
└─src
    ├─main
    │  ├─java
    │  │  └─webrtc
    │  │      └─signaling
    │  │          │  SignalingApplication.java
    │  │          │
    │  │          ├─config
    │  │          │      WebSocketConfig.java
    │  │          │
    │  │          ├─controller
    │  │          │      ClientController.java
    │  │          │      SignallingController.java
    │  │          │      WebcamController.java
    │  │          │
    │  │          ├─domain
    │  │          │      Webcam.java
    │  │          │
    │  │          ├─event
    │  │          │      WebcamChangedEvent.java
    │  │          │      WebcamChangedEventListener.java
    │  │          │      WebSocketEventListener.java
    │  │          │
    │  │          └─repository
    │  │                  WebcamKeyRepository.java
    │  │                  WebcamRepository.java
    │  │
    │  └─resources
    │      │  application.properties
    │      │
    │      ├─static
    │      │      client.js
    │      │      webcam.js
    │      │
    │      └─templates
    │          ├─client
    │          │      channel-choose.html
    │          │      channel.html
    │          │      limit.html
    │          │      redirect.html
    │          │
    │          └─webcam
    │                  webcam-streaming.html
    │
    └─test

```
* **WebSocketConfig.java**
  * 스프링 웹 소켓 설정 및 Stomp 브로커 설정


* **ClientController.java**
  * "/client", "/client/{displayInfo}", "/client/redirect" url 요청 시 실행되는 컨트롤러
  * clientChannels: 연결된 모든 웹캠의 display 정보를 가져와 전달
  * clientChannel: 현재 웹캠과 연결된 사용자의 수를 체크, 웹캠과 메시지를 주고 받기 위해 필요한 웹캠 id 전달 


* **SignallingController.java**
  * sendOffer: offer 전달을 위한 메시지 매핑 컨트롤러
  * sendAnswer: answer 전달을 위한 메시지 매핑 컨트롤러
  * sendIceCandidate: ice candidate 전달을 위한 메시지 매핑 컨트롤러
  * webcamConnectionInitiate: 초기 웹캠이 연결될 때, 고유 id, display 정보를 웹캠 레포지토리에 저장
  * webcamConnected: 사용자와 P2P 연결을 맺었을 때, 웹캠이 해당 컨트롤러로 사용자 id 전달, 웹캠 레포지토리의 객체에 사용자 추가
  * webCamDisconnected: 사용자와의 P2P 연결이 끊어졌을 때, 웹캠이 해당 컨트롤러로 사용자 id 전달, 웹캠 레포지토리의 객체에 사용자 제거


* **Webcam.java**
  * 웹캠 정보 객체
  * WebcamSessionId: 웹캠과 스프링 부트 웹소켓 연결 아이디
  * webcamKey: 메시지를 주고 받기 위한 웹캠 고유 id, 웹캠은 해당 id로 stomp 채널을 구독
  * displayInfo: 사용자가 "/client"에서 보는 display 정보
  * clientList: 웹캠에 연결된 사용자의 id를 저장한 리스트


* **WebcamChangedEvent.java, WebcamChangedEventListener.java**
  * WebcamRepository에서 웹캠이 저장되거나 삭제 될 때, 이벤트 리스너가 실행
  * "/client" 페이지(channel.html)에서 사용자는 시그널링 서버와 웹소켓 연결을 맺는데, 해당 웹소켓으로 변경된 웹캠 정보 동적으로 전송


* **WebSocketEventListener.java**
  * handleStompSubscribeListener: 채널 구독을 감지하고 로그 출력
  * handleWebSocketDisconnectListener: 웹소켓 연결이 끊김을 감지하고, 웹캠인 경우 레포지토리에서 제거


* **WebcamRepository.java**
  * Webcam 객체 저장소


* **client.js, channel-choose.html**
  * "/client/{displayInfo}" url 접속 시 실행
  * javascript webrtc로 웹캠과 P2P 연결을 맺고 영상 스트리밍


* **webcam.js, webcam-streaming.html**
  * "/webcam" url 접속 시 실행
  * javascript webrtc로 사용자와 P2P 연결을 맺고 영상 전송


* **channel.html**
  * "/client" url 접속 시 실행
  * 웹캠 정보 display


* **limit.html**
  * "/client/{displayInfo}" url 접속
  * 허용된 인원을 초과하면 실행


* **redirect.html**
  * 연결 상태 변경 시, "/client/redirect"로 연결되며 실행
