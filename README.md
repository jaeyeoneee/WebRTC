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
* 스프링 부트 stomp 서버를 sdp를 교환하는 signaling 서버로 한다.
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
* 웹캠 접속(/webcam)

* 사용자 웹캠 선택(/client)
* 사용자 웹캠 영상 스트리밍
## 주요 기능

